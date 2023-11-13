#!/bin/bash

# defined colors
NO_COLOR_CODE='\033[0m'
COLOR_RED_CODE='\033[0;31m'
COLOR_GREEN_CODE='\033[0;32m'
COLOR_YELLOW_CODE='\033[1;33m'
CURRENT_FILE_PATH=$(dirname $0)
CURRENT_PROJECT_PATH=$(pwd)

JAVA_VERSION=$(java -version 2>&1 | head -1 | cut -d'"' -f2 | sed '/^1\./s///' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt "11" ]; then
    echo -e "${COLOR_RED_CODE}[NOK]${NO_COLOR_CODE} Por favor use la version 11 de Java o superior"
    exit 1
else
    echo -e "${COLOR_GREEN_CODE}[OK]${NO_COLOR_CODE} La version de Java es 11 o superior"
fi

set -eou
for arg in "$@"; do
    case $arg in
    --application-sonar=*)
        export APPLICATION_SONAR="${arg#*=}"
        ;;
    --access-token=*)
        export SONAR_TOKEN="${arg#*=}"
        ;;
    --project-name=*)
        export SONAR_CLOUD_PROJECT_NAME="${arg#*=}"
        ;;
    --project-key=*)
        export SONAR_CLOUD_PROJECT_KEY="${arg#*=}"
        ;;
    --organization=*)
        export SONAR_CLOUD_ORG="${arg#*=}"
        ;;
    --current-branch=*)
        export CURRENT_BRANCH="${arg#*=}"
        ;;
    --language=*)
        export PROJECT_LANGUAGE="${arg#*=}"
        ;;
    --coverage-file=*)
        export COVERAGE_FILE="${arg#*=}"
        ;;
    --debug=*)
        export DEBUG="${arg#*=}"
        ;;
    --sonar-project-properties-file=*)
        export PROJECT_PROPERTIES_FILE="${arg#*=}"
        ;;
    esac
    shift
done
if [ "$DEBUG" = "true" ]; then
    set -eoux
fi

showUsage() {
    echo " Make sure you are passing all required parameters. Check the usage:
        run-sonar-cloud.sh \\
            --application-sonar=(not required) \\
            --access-token=(required) \\
            --project-name=(required) \\
            --project-key=(required) \\
            --organization=(required) \\
            --current-branch=(required) \\
            --language=(required) \\
            --coverage-file=(not required) \\
            --debug=(not required) \\
            --sonar-project-properties-file=(not required) \\

        You currently passed:
        --application-sonar: $APPLICATION_SONAR
        --access-token: ***
        --project-name: $SONAR_CLOUD_PROJECT_NAME
        --project-key: $SONAR_CLOUD_PROJECT_KEY
        --organization: $SONAR_CLOUD_ORG
        --current-branch: $CURRENT_BRANCH
        --language: $PROJECT_LANGUAGE
        --coverage-file: $COVERAGE_FILE
        --debug: $DEBUG
        --sonar-project-properties-file: $PROJECT_PROPERTIES_FILE
    " 1>&2
}

validateArgs() {
    if [ -z "$CURRENT_BRANCH" ] || [ -z "$APPLICATION_SONAR" ] || [ -z "$SONAR_TOKEN" ] || [ -z "$SONAR_CLOUD_PROJECT_NAME" ] || [ -z "$SONAR_CLOUD_PROJECT_KEY" ] || [ -z "$SONAR_CLOUD_ORG" ] || [ -z "$CURRENT_BRANCH" ] || [ -z "$PROJECT_LANGUAGE" ]; then
        showUsage
        exit 1
    fi
}

setStaticValues() {
    if [ "$APPLICATION_SONAR" = 'sonarqube' ]; then
         echo "set static value for post project in SonarQube"
        SONAR_ENDPOINT='https://bdb-pr-webapp-sonar-iac.azurewebsites.net'
    else
        echo "set static value for post project in SonarCloud"
        SONAR_ENDPOINT='https://sonarcloud.io'
    fi
}

# rationale: find if the project has a sonar-project.properties file
setDefaultCoverageFile() {
    if [ "$COVERAGE_FILE" = '' ]; then
        echo "No custom coverage file detected"
        DEFAULT_COVERAGE_FILE="$COVERAGE_FILE"
        if [ "$PROJECT_LANGUAGE" = 'javascript' ]; then
            DEFAULT_COVERAGE_FILE='coverage/lcov.info'
            echo "Setting coverage file: $DEFAULT_COVERAGE_FILE"
        else
            echo "No default coverage file for $PROJECT_LANGUAGE language"
        fi
        export COVERAGE_FILE="$DEFAULT_COVERAGE_FILE"
    fi
}
set
verifySonarProjectProperties() {
    if [ "$PROJECT_PROPERTIES_FILE" = '' ]; then
        echo "No custom project properties file detected"
        if [ -n "$PROJECT_LANGUAGE" ]; then
            echo "Using default sonar-project-$PROJECT_LANGUAGE.properties configuration file"
            cp "$CURRENT_FILE_PATH/sonar-project-$PROJECT_LANGUAGE.properties" sonar-project.properties
            setDefaultCoverageFile
        else
            echo 'You must provide a sonar-project.properties file or a programming language'
            exit 1
        fi
    else
        echo "Using custom $PROJECT_PROPERTIES_FILE configuration file"
        cp "$PROJECT_PROPERTIES_FILE" sonar-project.properties
    fi
}

installSonarScanner() {
    echo "Downloading sonar-scanner....."
    if [ -d "/tmp/sonar-scanner-cli-3.2.0.1227-linux.zip" ];then
        rm /tmp/sonar-scanner-cli-3.2.0.1227-linux.zip
    fi
    wget -q https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-4.6.2.2472.zip -P /tmp
    echo "Download completed."
    echo "Unziping downloaded file..."
    unzip /tmp/sonar-scanner-cli-4.6.2.2472.zip -d /tmp
    echo "Unzip completed."
    rm /tmp/sonar-scanner-cli-4.6.2.2472.zip
    echo "Installing to opt..."
    if [ -d "/var/opt/sonar-scanner-4.6.2.2472" ];then
        rm -rf /var/opt/sonar-scanner-4.6.2.2472
    fi
    sudo mv /tmp/sonar-scanner-4.6.2.2472 /var/opt
    rm -rf /tmp/sonar-scanner-4.6.2.2472
    echo "Installation completed successfully."
    echo "You can use sonar-scanner!"
}

# rationale: execute functions in order
validateArgs
setStaticValues
verifySonarProjectProperties
installSonarScanner

echo 'Start reporting to SonarCloud!'
/var/opt/sonar-scanner-4.6.2.2472/bin/sonar-scanner -Dsonar.host.url="$SONAR_ENDPOINT"

# rationale: check if has the right permissions
checkPermissions() {
    TEST_PROJECT_PERMISSIONS=$(curl -u "$SONAR_TOKEN": "$SONAR_ENDPOINT/api/qualitygates/project_status?projectKey=$SONAR_CLOUD_PROJECT_KEY&branch=master")
    echo "$TEST_PROJECT_PERMISSIONS"
    if [[ "$TEST_PROJECT_PERMISSIONS" == *"Insufficient privileges"* ]]; then
        echo -e "${COLOR_RED_CODE}[NOK]${NO_COLOR_CODE} Insufficient privileges or project does not exist"
        exit 1
    fi
}
checkPermissions

# rationale: get analysis result via SonarCloud REST API
TASK_URL=$(grep <.scannerwork/report-task.txt -i ceTaskUrl | sed 's/ceTaskUrl=//')
echo "TASK_URL: $TASK_URL"
ANALYSES_URL="$SONAR_ENDPOINT/api/project_analyses/search?project=$SONAR_CLOUD_PROJECT_NAME&branch=$CURRENT_BRANCH"
echo "ANALYSES_URL: $ANALYSES_URL"
MAX_ATTEMPTS=13
TIME_WAIT_EXPONENTIAL=1
retryGetAlertStatus() {
    if [ "$MAX_ATTEMPTS" -gt "9" ]; then
        TIME_WAIT=$((2**$TIME_WAIT_EXPONENTIAL))
        TIME_WAIT_EXPONENTIAL=$((TIME_WAIT_EXPONENTIAL + 1))
    else
        TIME_WAIT=30
    fi
    if [ "$MAX_ATTEMPTS" = "0" ]; then
        echo 'Timeout for get status data...'
        exit 1
    fi
    echo "Waiting $TIME_WAIT seconds for SonarCloud response... REMAINING_ATTEMPTS: $MAX_ATTEMPTS"
    sleep $TIME_WAIT
    MAX_ATTEMPTS=$((MAX_ATTEMPTS - 1))
    getAlertStatus
    
}
getAlertStatus() {
    TASK=$(curl -u "$SONAR_TOKEN": "$TASK_URL")
    BRANCH_TYPE=$(echo "$TASK" | jq -r .task.branchType)
    if [ "$BRANCH_TYPE" = "SHORT" ]; then
        if [ "$CURRENT_BRANCH" != "master" ] && [ "$CURRENT_BRANCH" != "staging" ] && [ "$CURRENT_BRANCH" != "qa" ]
        then
            echo "$CURRENT_BRANCH is a short-lived branch, quality validation is not currently supported in this orb, you can see the results at sonarcloud."
            exit 1
        else
            echo "$CURRENT_BRANCH is a short-lived branch, Long living branches pattern must be set as: (master|staging|qa) by an administrator, you can see the results at sonarcloud."
            exit 1
        fi
    fi
    ANALYSIS_ID=$(echo "$TASK" | jq -r .task.analysisId)
    echo "ANALYSIS_ID: $ANALYSIS_ID"
    if [ "$ANALYSIS_ID" = "null" ]; then
        retryGetAlertStatus
    fi
    ANALYSES=$(curl -u "$SONAR_TOKEN": "$ANALYSES_URL")
    ANALYSIS=$(echo "$ANALYSES" | jq -c '.analyses | map(select(.key=="'"${ANALYSIS_ID}"'"))[0]')
    echo "ANALYSIS: $ANALYSIS"
    if [ "$ANALYSIS" = "null" ]; then
        retryGetAlertStatus
    fi
    ANALYSIS_DATE=$(echo "$ANALYSIS" | jq -r .date)
    ANALYSIS_DATE_TO_URL=${ANALYSIS_DATE//+/%2B}
    #ANALYSIS_DATE_TO_URL=$(echo $ANALYSIS_DATE | sed 's/+/%2B/g')
    HISTORY_URL="$SONAR_ENDPOINT/api/measures/search_history?component=$SONAR_CLOUD_PROJECT_NAME&from=$ANALYSIS_DATE_TO_URL&metrics=alert_status&ps=1000&branch=$CURRENT_BRANCH"
    echo "HISTORY_URL: $HISTORY_URL"
    HISTORY_ALERT_STATUS=$(curl -u "$SONAR_TOKEN": "$HISTORY_URL")
    ALERT_STATUS=$(echo "$HISTORY_ALERT_STATUS" | jq -r -c '.measures[0].history | map(select(.date=="'"${ANALYSIS_DATE}"'"))[0].value')
    if [ "$ALERT_STATUS" = "null" ]; then
        retryGetAlertStatus
    fi
}
getAlertStatus

# rationale: validate analysis status result
if [ "$ALERT_STATUS" = 'OK' ]; then
    echo -e "${COLOR_GREEN_CODE}[OK]${NO_COLOR_CODE} Quality checks success"
elif [ "$ALERT_STATUS" = 'ERROR' ]; then
    echo -e "${COLOR_RED_CODE}[NOK]${NO_COLOR_CODE} Quality checks failed"
    exit 1
else
    echo -e "${COLOR_RED_CODE}[INVALID ALERT STATUS]${NO_COLOR_CODE} ALERT_STATUS: $ALERT_STATUS"
    exit 1
fi

