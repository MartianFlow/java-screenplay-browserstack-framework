mkdir sonarcloud-pipeline
cp *.sh sonarcloud-pipeline
cp *.properties sonarcloud-pipeline
bash run-sonar-cloud.sh \
    --application=sonarcloud \
    --current-branch=main \
    --access-token=$SONAR_ACCESS_TOKEN \
    --project-name=test \
    --project-key=test \
    --organization=martianflowdemo \
    --language=java \
    --coverage-file='' \
    --debug=true
rm -rf sonarcloud-pipeline
rm sonar-project.properties
