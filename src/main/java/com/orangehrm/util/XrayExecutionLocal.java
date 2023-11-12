package com.orangehrm.util;


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
        String bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZW5hbnQiOiJiZTYyOWQyNy00NmY5LTNmNDItYWJmNC1kZTJi"
                + "NTkzNWY4MzQiLCJhY2NvdW50SWQiOiI2MmJkYTFiZTllNmJhMzRjOTkzNzQyMjUiLCJpc1hlYSI6ZmFsc2UsImlhdCI6MTY5NzU2"
                + "MTE5NywiZXhwIjoxNjk3NjQ3NTk3LCJhdWQiOiJDRTE0RDAwMEFFRTM0OTAzOUNFQzdDMjU2ODI0QTlDRiIsImlzcyI6ImNvbS54"
                + "cGFuZGl0LnBsdWdpbnMueHJheSIsInN1YiI6IkNFMTREMDAwQUVFMzQ5MDM5Q0VDN0MyNTY4MjRBOUNGIn0._TGodLjjwBRYd2Vr"
                + "gYwZ405KX-i0psVocKx7iX4P4W4";
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
