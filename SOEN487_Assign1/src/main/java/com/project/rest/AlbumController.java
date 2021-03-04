package com.project.rest;

import impl.factory.AlbumDBRepoFactory;
import impl.factory.AlbumRepoFactory;
import lib.models.Album;
import lib.repos.db.AlbumRepo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


@Path("album")
public class AlbumController {

    private static AlbumRepo repo = setup();

    private static AlbumRepo setup(){
        InputStream input = null;
        try {
            input = new FileInputStream("config/config.properties");
            Properties prop = new Properties();
            prop.load(input);
            return AlbumDBRepoFactory.getInstance(prop);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("all")
    public String getAlbums() {
        try {
            List<Album> albums = repo.readAll();

            StringBuilder sb = new StringBuilder();
            for (Album a : albums) {
                sb.append(a.toString() + "\n\n");
            }
            return sb.toString();
        }catch (Exception e){
            return "Failed operations";
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{isrc}")
    public String getDetails(@PathParam("isrc") String isrc) {
        try {
            Album a = repo.read(isrc);
            return a.toString();
        }catch (Exception e){
            return "Operation Failed.";
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addAlbum(Album album) {

        try {
            this.repo.add(album);
            return "New Album added";
        }catch (Exception e){
            return "Operation Failed.";
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String editAlbum(Album album){

        try {
            this.repo.update(album);
            return "Updated album";
        }catch (Exception e){
            return "Operation Failed.";
        }
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{isrc}")
    public String deleteAlbum(@PathParam("isrc") String isrc) {
        try {
            this.repo.delete(isrc);
            return "Deleted album";
        }catch (Exception e){
            return "Operation Failed.";
        }
    }


}
