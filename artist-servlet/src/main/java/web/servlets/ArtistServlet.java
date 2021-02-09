package web.servlets;

import impl.factory.ArtistRepoFactory;
import lib.models.Artist;
import lib.repos.ArtistRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "artist")
public class ArtistServlet extends HttpServlet {

    private ArtistRepo repo;

    @Override
    public void init() {
        repo = ArtistRepoFactory.getInstance();
    }

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nickname = request.getParameter("nickname");
        List<Artist> artistList = new ArrayList<>();
        if(nickname == null) {
            artistList = repo.readAll();
        }
        else {
            Artist artist = repo.read(nickname);
            if(artist != null)
                artistList.add(artist);
        }

        String jsonResponse = "{ artists: [ ";

        for (int i = 0; i < artistList.size(); i++) {
            if(i == 0)
                jsonResponse = jsonResponse + artistList.get(i);
            else
                jsonResponse = jsonResponse + " , " + artistList.get(i);
        }

        jsonResponse = jsonResponse + " ] }";
        sendResponse(response, jsonResponse);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nickname = request.getParameter("nickname");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String bio = request.getParameter("bio");

        Artist artist = new Artist();
        artist.setNickname(nickname);
        artist.setFirstname(firstname);
        artist.setLastname(lastname);
        artist.setBio(bio);

        repo.update(artist);
        sendResponse(response, "Artist updated.");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String[] data = br.readLine().split("&");
        HashMap hashMap = new HashMap();

        for (int i = 0; i < data.length; i++) {
            String[] pair = data[i].split("=");
            hashMap.put(pair[0], pair[1]);
        }

        System.out.println((String)hashMap.get("nickname"));

        Artist artist = new Artist();
        artist.setNickname((String)hashMap.get("nickname"));
        artist.setFirstname((String)hashMap.get("firstname"));
        artist.setLastname((String)hashMap.get("lastname"));
        artist.setBio((String)hashMap.get("bio"));

        repo.add(artist);
        sendResponse(response, "Artist added.");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nickname = request.getParameter("nickname");

        if(nickname != null){
            repo.delete(nickname);
            sendResponse(response, "Artist deleted.");
        }else{
            sendResponse(response, "No artist provided.");
        }
    }

    private void sendResponse(HttpServletResponse response, String result) throws IOException{
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.append(result);
    }
}
