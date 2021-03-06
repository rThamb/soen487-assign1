package lib.util;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.utils.URIBuilder;



//https://mkyong.com/java/apache-httpclient-examples/
public class MyHttpClient {
    public String get(String url) throws Exception {
        HttpGet request = new HttpGet(encode(url));
        return send(request);
    }

    public String put(String url, HashMap<String, String> payload) throws Exception {
        HttpPut putRequest = new HttpPut(encode(url));

        //request parameters for put
        List<NameValuePair> urlParameters = new ArrayList<>();
        //to iterate through payload
        Iterator hashMapIterator = payload.entrySet().iterator();

        while(hashMapIterator.hasNext()){
            Map.Entry hashMapElement = (Map.Entry) hashMapIterator.next();
            urlParameters.add(new BasicNameValuePair((String) hashMapElement.getKey(), (String) hashMapElement.getValue()));
        }

        //set parameters as given from payload
        putRequest.setEntity(new UrlEncodedFormEntity(urlParameters,"UTF-8"));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(putRequest)){

            return handleResponse(response);
        }
    }

    public String post(String url, HashMap<String, String> payload) throws Exception{
        HttpPost postRequest = new HttpPost(encode(url));

        //request parameters for post
        List<NameValuePair> urlParameters = new ArrayList<>();
        //to iterate through payload
        Iterator hashMapIterator = payload.entrySet().iterator();

        while(hashMapIterator.hasNext()){
            Map.Entry hashMapElement = (Map.Entry) hashMapIterator.next();
            urlParameters.add(new BasicNameValuePair((String) hashMapElement.getKey(), (String) hashMapElement.getValue()));
        }

        //set parameters as given from payload
        postRequest.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(postRequest)){

            return handleResponse(response);
        }
    }

    public String delete(String url) throws Exception{
        HttpDelete request = new HttpDelete(encode(url));
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

    public String putJson(String url, String jsonData) throws Exception {
        StringEntity requestEntity = new StringEntity(
                jsonData,
                ContentType.APPLICATION_JSON);

        HttpPut putMethod = new HttpPut(encode(url));
        putMethod.setEntity(requestEntity);
        return send(putMethod);
    }

    public String postJson(String url, String jsonData) throws Exception {
        StringEntity requestEntity = new StringEntity(
                jsonData,
                ContentType.APPLICATION_JSON);

        HttpPost postMethod = new HttpPost(encode(url));
        postMethod.setEntity(requestEntity);

        return send(postMethod);
    }

    private static String encode(String urlStr) throws MalformedURLException, URISyntaxException {
        URL url= new URL(urlStr);
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        return uri.toASCIIString();

    }

    public String postMultipart(String url, File file) throws IOException {
        HttpPost httppost = new HttpPost(url);

        FileBody bin = null;

        if(file != null)
            bin = new FileBody(file);

        //StringBody fields = new StringBody("", ContentType.TEXT_PLAIN);

        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("coverImage", bin)
                //.addPart("comment", comment)
                .build();

        httppost.setEntity(reqEntity);
        String response = send(httppost);
        return response;
    }

    public String sendXML(String urlStr, String xmlPayload) throws Exception{
/*
        HttpEntity entity = new HttpEntity(xmlPayload, ContentType.create("text/xml", Consts.UTF_8));
        new HttpEntity(xmlPayload, "text/xml");
        HttpEntity reqEntity = E
                .addPart("coverImage", bin)



        HttpEntity request = RequestBuilder.


        return "";

*/

        String request = xmlPayload;

        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set timeout as per needs
        connection.setConnectTimeout(20000);
        connection.setReadTimeout(20000);

        // Set DoOutput to true if you want to use URLConnection for output.
        // Default is false
        connection.setDoOutput(true);

        connection.setUseCaches(true);
        connection.setRequestMethod("POST");

        // Set Headers
        connection.setRequestProperty("Accept", "application/xml");
        connection.setRequestProperty("Content-Type", "application/xml");

        // Write XML
        OutputStream outputStream = connection.getOutputStream();
        byte[] b = request.getBytes("UTF-8");
        outputStream.write(b);
        outputStream.flush();
        outputStream.close();

        // Read XML
        InputStream inputStream = connection.getInputStream();
        byte[] res = new byte[2048];
        int i = 0;
        StringBuilder response = new StringBuilder();
        while ((i = inputStream.read(res)) != -1) {
            response.append(new String(res, 0, i));
        }
        inputStream.close();

        System.out.println("Response= " + response.toString());
        return response.toString();
    }

    public String sendXMLV2(String url, String payload) throws IOException {

        HttpPost postRequest = new HttpPost(url);

        postRequest.addHeader("content-type", "application/xml");

        StringEntity userEntity = new StringEntity(payload);
        postRequest.setEntity(userEntity);

        String response = send(postRequest);

        return response;
    }
}
