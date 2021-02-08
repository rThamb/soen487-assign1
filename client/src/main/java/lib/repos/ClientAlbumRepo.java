package lib.repos;

import lib.models.Album;
import lib.util.MyHttpClient;

import java.io.IOException;
import java.util.List;

public class ClientAlbumRepo {

    private MyHttpClient httpClient;
    private String url = "http://localhost:8080/api/album";

    public ClientAlbumRepo(){
        this.httpClient = new MyHttpClient();
    }

    public String listAlbums() throws IOException {
        String url = "";
        String response = this.httpClient.get(url);
        return response;
    }

    public String getAlbum(String isrc) throws IOException {
        String url = "";
        String response = this.httpClient.get(url);
        return response;
    }

    public String add(Album a){
        String url = "";
        String response = this.httpClient.post(null, null);
        return "";
    }

    public String edit(Album a){
        return "";
    }

    public String delete(String id){
        return "";
    }

}
