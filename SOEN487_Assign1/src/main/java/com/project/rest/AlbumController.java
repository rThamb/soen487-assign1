package com.project.rest;

import com.project.rest.responses.AlbumCollection;
import com.project.rest.responses.Message;
import com.project.rest.responses.WebError;
import com.sun.jersey.multipart.FormDataParam;
import impl.factory.AlbumDBRepoFactory;
import lib.exception.RepException;
import lib.models.Album;
import lib.repos.db.AlbumRepo;
import lib.web.JSONifiable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
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
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public Response getAlbums() throws RepException{
        try {
            List<Album> albums = repo.readAll();
            //returning binary data breaks endpoint response
            for(Album a: albums)
                a.setCoverImage(null);
            return albumCollectionResponse(albums);
        }catch (Exception e){
           return errorResponse(e);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{isrc}")
    public Response getDetails(@PathParam("isrc") String isrc) {
        try {
            Album a = repo.read(isrc);

            //error when binary array present
            a.setCoverImage(null);
            return albumResponse(a);
        }catch (Exception e){
            return errorResponse(e);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAlbum(Album album) {

        try {
            this.repo.add(album);
            return successOperation("New Album added");
        }catch (Exception e){
            return errorResponse(e);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editAlbum(Album album){

        try {
            this.repo.update(album);
            return successOperation("Updated album");
        }catch (Exception e){
            return errorResponse(e);
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{isrc}")
    public Response deleteAlbum(@PathParam("isrc") String isrc) {
        try {
            this.repo.delete(isrc);
            return successOperation("Deleted album");
        }catch (Exception e){
            return errorResponse(e);
        }
    }

    @POST
    @Path("/upload/{isrc}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String uploadFile(@FormDataParam("coverImage") InputStream inputStream, @PathParam("isrc") String isrc){
        return "";
    }

    private Response errorResponse(Exception e){
        WebError error = new WebError("RepException", "Failed Operation: \n\n" + e.getMessage());
        return Response.status(200).entity(error).build();
    }

    private Response albumCollectionResponse(List<Album> a){
        return Response.status(200).entity(new AlbumCollection(a)).build();
    }

    private Response albumResponse(Album a){
        return Response.status(200).entity(a).build();
    }

    private Response successOperation(String mes){
        return Response.status(200).entity(new Message(mes)).build();
    }


}
