package lib.repos;

import lib.models.Album;
import lib.util.MyHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ClientAlbumRepo {

    private MyHttpClient httpClient;
    private String url = "http://localhost:8080/api/album";

    public ClientAlbumRepo(){
        this.httpClient = new MyHttpClient();
    }

    public String listAlbums() throws Exception {
        String url = this.url + "/all";
        String response = this.httpClient.get(url);
        return response;
    }

    public String getAlbum(String isrc) throws Exception {
        String url = this.url + "/" + isrc;
        String response = this.httpClient.get(url);
        return response;
    }

    public String add(String[] input) throws IOException {
        String str = "{ \"isrc\": \"%s\", \"title\": \"%s\", \"description\": \"%s\", \"year\": %s }";
        String payload = String.format(str, input[0], input[1], input[2], input[3]);
        String response = this.httpClient.putJson(url, payload);
        return response;
    }

    public String edit(String[] input) throws IOException {
        String str = "{ \"isrc\": \"%s\", \"title\": \"%s\", \"description\": \"%s\", \"year\": %s }";
        String payload = String.format(str, input[0], input[1], input[2], input[3]);
        String response = this.httpClient.postJson(url, payload);
        return response;
    }

    public String delete(String isrc) throws IOException {
        String url = this.url + "/" + isrc;
        String response = this.httpClient.delete(url);
        return response;
    }

}
