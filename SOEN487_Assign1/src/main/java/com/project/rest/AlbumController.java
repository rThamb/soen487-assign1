package com.project.rest;

import impl.factory.AlbumRepoFactory;
import lib.models.Album;
import lib.repos.AlbumRepo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("album")
public class AlbumController {

    private AlbumRepo repo = AlbumRepoFactory.getInstance();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("all")
    public String getAlbums() {
        List<Album> albums = repo.readAll();

        StringBuilder sb = new StringBuilder();
        for(Album a: albums){
            sb.append(a.toString() + "\n\n");
        }
        return sb.toString();
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
