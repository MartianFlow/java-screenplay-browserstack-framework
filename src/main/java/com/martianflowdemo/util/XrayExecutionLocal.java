package com.martianflowdemo.util;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XrayExecutionLocal {

    private XrayExecutionLocal() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(XrayExecutionLocal.class);


    public static void sendPostRequest(String projectKey, String testPlanKey, String testExecKey) throws IOException {

        String apiUrl = "https://xray.cloud.getxray.app/api/v2/import/execution/testng?projectKey=" + projectKey
                + "&testPlanKey=" + testPlanKey
                + "&testExecKey=" + testExecKey;
        String filePath = "target/serenity-reports/cucumber_report.json";
        String bearerToken = "${secrets.BEARER_TOKEN}";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(apiUrl);

        httpPost.addHeader("Authorization", "Bearer " + bearerToken);
        httpPost.addHeader("Content-Type", "application/xml");

        String xmlData = Utils.readFileContent(filePath);
        StringEntity entity = new StringEntity(xmlData, ContentType.APPLICATION_XML);
        httpPost.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                LOGGER.info("Ok http peticion xray ok");
            }
        } finally {
            httpClient.close();
        }
    }

    private static class Utils {
        private static String readFileContent(String filePath) throws IOException {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        }
    }
}
