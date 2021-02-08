package lib.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;

//https://mkyong.com/java/apache-httpclient-examples/
public class MyHttpClient {
    public String get(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        return send(request);
    }

    public String put(String url) throws IOException {
        HttpPut request = new HttpPut(url);
        return send(request);
    }

    public String post(String url, HashMap<String, String> payload){
        return "";
    }
    public String delete(String url){
        return "";
    }

    private String send(HttpUriRequest request) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

            if(response.getStatusLine().getStatusCode() > 400 ){
                throw new IOException("Failed Request: 200 code not returned");
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                return result;
            }
            else
                return "";
        }
    }
}
