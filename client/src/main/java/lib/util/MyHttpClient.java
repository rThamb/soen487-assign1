package lib.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.utils.URIBuilder;



//https://mkyong.com/java/apache-httpclient-examples/
public class MyHttpClient {
    public String get(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        return send(request);
    }

    public String put(String url, HashMap<String, String> payload) throws IOException {
        HttpPut putRequest = new HttpPut(url);

        //request parameters for put
        List<NameValuePair> urlParameters = new ArrayList<>();
        //to iterate through payload
        Iterator hashMapIterator = payload.entrySet().iterator();

        while(hashMapIterator.hasNext()){
            Map.Entry hashMapElement = (Map.Entry) hashMapIterator.next();
            urlParameters.add(new BasicNameValuePair((String) hashMapElement.getKey(), (String) hashMapElement.getValue()));
        }

        //set parameters as given from payload
        putRequest.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(putRequest)){

            return handleResponse(response);
        }
    }

    public String post(String url, HashMap<String, String> payload) throws IOException{
        HttpPost postRequest = new HttpPost(url);

        //request parameters for post
        List<NameValuePair> urlParameters = new ArrayList<>();
        //to iterate through payload
        Iterator hashMapIterator = payload.entrySet().iterator();

        while(hashMapIterator.hasNext()){
            Map.Entry hashMapElement = (Map.Entry) hashMapIterator.next();
            urlParameters.add(new BasicNameValuePair((String) hashMapElement.getKey(), (String) hashMapElement.getValue()));
        }

        //set parameters as given from payload
        postRequest.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(postRequest)){

            return handleResponse(response);
        }
    }

    public String delete(String url) throws IOException{
        HttpDelete request = new HttpDelete(url);
        return send(request);
    }

    private String send(HttpUriRequest request) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

            return handleResponse(response);
        }
    }

    private String handleResponse(CloseableHttpResponse response) throws IOException {
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

    public String putJson(String url, String jsonData) throws IOException {
        StringEntity requestEntity = new StringEntity(
                jsonData,
                ContentType.APPLICATION_JSON);

        HttpPut putMethod = new HttpPut(url);
        putMethod.setEntity(requestEntity);
        return send(putMethod);
    }

    public String postJson(String url, String jsonData) throws IOException {
        StringEntity requestEntity = new StringEntity(
                jsonData,
                ContentType.APPLICATION_JSON);

        HttpPost postMethod = new HttpPost(url);
        postMethod.setEntity(requestEntity);

        return send(postMethod);
    }

    private String encode(String urlStr) throws MalformedURLException, URISyntaxException {
        URL url= new URL(urlStr);
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        return uri.toASCIIString();

    }
}
