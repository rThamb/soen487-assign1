package com.project.client;

import java.io.IOException;
import java.net.*;


public class BookClient {

    private String baseUrl = "http://localhost:8080/library/book/";
    private MyHttpClient client;

    public BookClient(){
        this.client = new MyHttpClient();
    }

    public String getBookByTitle(String title){
        try {
            String url = encode(baseUrl + title);
            return client.get(url);
        } catch (MalformedURLException e) {
            return "Failed to Send GET request. ()";
        } catch (IOException e) {
            return "Failed to Send GET request. (IOException)";
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "Failed";
        }
    }


    public String addBook(String title, String author, String isbn){
        try {
            String url = encode(baseUrl + title + "/" + author + "/" + isbn);
            client.put(url);
            return "Saved Book: " + isbn + ":" + title;
        } catch (URISyntaxException | MalformedURLException e) {
            return "Failed to Send PUT request. (IOException)";
        } catch (IOException e) {
            return "Failed to Send PUT request. (IOException)";
        }
    }




    private String encode(String urlStr) throws MalformedURLException, URISyntaxException {

        URL url= new URL(urlStr);
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        return uri.toASCIIString();

    }

}
