package org.wso2.carbon.solution.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SampleFileUpload {

    public static void whenSendMultipartRequestUsingHttpClient_thenCorrect() {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:8080/manager/html/upload");
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", new File("/home/harshat/wso2/demo-suite/apache-tomcat-8.0.36/sample.war"),
                                  ContentType.MULTIPART_FORM_DATA, "sample.war");
            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);
            CloseableHttpResponse response = client.execute(httpPost);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cmain(String... args) throws IOException {
        whenSendMultipartRequestUsingHttpClient_thenCorrect();
    }

    static final String UPLOAD_URL = "http://localhost:8080/manager/html/upload";
    static final int BUFFER_SIZE = 4000096;

    public static void mai2n(String[] args) throws IOException {
        // takes file path from first program's argument
        try {
            String filePath = "/home/harshat/wso2/demo-suite/apache-tomcat-8.0.36/sample.war";
            File uploadFile = new File(filePath);

            System.out.println("File to upload: " + filePath);

            // creates a HTTP connection
            URL url = new URL(UPLOAD_URL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true);
            httpConn.setRequestMethod("POST");
            // sets file name as a HTTP header
            httpConn.setRequestProperty("deployWar", uploadFile.getName());

            // opens output stream of the HTTP connection for writing data
            OutputStream outputStream = httpConn.getOutputStream();

            // Opens input stream of the file for reading data
            FileInputStream inputStream = new FileInputStream(uploadFile);

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

            System.out.println("Start writing data...");

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            Thread.sleep(5000);
            System.out.println("Data was written.");
            outputStream.close();
            inputStream.close();
            Thread.sleep(5000);
            // always check HTTP response code from server
            int responseCode = httpConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // reads server's response
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        httpConn.getInputStream()));
                String response = reader.readLine();
                System.out.println("Server's response: " + response);
            } else {
                System.out.println("Server returned non-OK code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) throws IOException {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();

            File file = new File("/home/harshat/wso2/demo-suite/apache-tomcat-8.0.36/sample.war");
            // build multipart upload request
            HttpEntity data = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addBinaryBody("deployWar", file, ContentType.MULTIPART_FORM_DATA, file.getName())
                    .build();

            // build http request and assign multipart upload data
            HttpUriRequest request = RequestBuilder.post().setUri("http://localhost:8080/manager/html/upload")
                    .setEntity(data)
                    .build();

            System.out.println("Executing request " + request.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = httpclient.execute(request, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}