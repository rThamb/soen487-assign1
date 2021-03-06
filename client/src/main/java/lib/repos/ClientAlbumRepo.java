package lib.repos;

import lib.models.Album;
import lib.util.MyHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ClientAlbumRepo {

    private MyHttpClient httpClient;
    private String url = "http://localhost:8081/api/album";
    private String coverUrl = "http://localhost:8081/api/cover";

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

    public String add(String[] input) throws Exception {
        String str = "{\"isrc\":\"%s\",\"title\":\"%s\",\"description\":\"%s\",\"year\":\"%s\",\"artist\":{\"firstname\":\"%s\",\"lastname\":\"%s\"}}";
        String[] artist = input[4].split(",");
        String artist_first = artist[0];
        String artist_last = artist[1];
        String payload = String.format(str, input[0], input[1], input[2], input[3], artist_first, artist_last);
        String response = this.httpClient.putJson(url, payload);
        return response;
    }

    public String edit(String[] input) throws Exception {
        String str = "{\"isrc\":\"%s\",\"title\":\"%s\",\"description\":\"%s\",\"year\":\"%s\",\"artist\":{\"firstname\":\"%s\",\"lastname\":\"%s\"}}";
        String[] artist = input[4].split(",");
        String artist_first = artist[0];
        String artist_last = artist[1];
        String payload = String.format(str, input[0], input[1], input[2], input[3], artist_first, artist_last);
        String response = this.httpClient.postJson(url, payload);
        return response;
    }

    public String delete(String isrc) throws Exception {
        String url = this.url + "/" + isrc;
        String response = this.httpClient.delete(url);
        return response;
    }

    public String uploadFile(String isrc, File file) throws IOException {
        String url = this.coverUrl + "/upload/" + isrc;
        String response = httpClient.postMultipart(url, file);
        return response;
    }

    public String deleteFile(String isrc) throws Exception {
        String url = this.url + "/img/" + isrc;
        String response = this.httpClient.delete(url);
        return response;
    }


}
