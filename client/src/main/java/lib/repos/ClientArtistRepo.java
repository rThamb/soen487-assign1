package lib.repos;

import lib.util.MyHttpClient;
import java.util.HashMap;

public class ClientArtistRepo {

    private MyHttpClient httpClient;
    private String url = "http://localhost:8080/artist_servlet_war/artist";

    public ClientArtistRepo(){
        this.httpClient = new MyHttpClient();
    }

    public String listArtists() throws Exception {
        String url = this.url;
        String response = this.httpClient.get(url);
        return response;
    }

    public String getArtist(String nickname) throws Exception {
        String url = this.url + "?nickname=" + nickname;
        String response = this.httpClient.get(url);
        return response;
    }


    public String add(String[] input) throws Exception {
        String[] keys = {"nickname", "firstname", "lastname", "bio"};
        HashMap payload = new HashMap();

        for (int i = 0; i < keys.length; i++) {
            payload.put(keys[i], input[i]);
        }

        String response = this.httpClient.put(url, payload);
        return response;
    }

    public String edit(String[] input) throws Exception{
        String[] keys = {"nickname", "firstname", "lastname", "bio"};
        HashMap payload = new HashMap();

        for (int i = 0; i < keys.length; i++) {
            payload.put(keys[i], input[i]);
        }

        String response = this.httpClient.post(url, payload);
        return response;
    }

    public String delete(String nickname) throws Exception{
        String url = this.url + "?nickname=" + nickname;
        String response = this.httpClient.delete(url);
        return response;
    }
}
