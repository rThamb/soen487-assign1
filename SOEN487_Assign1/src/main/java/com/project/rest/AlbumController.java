package com.project.rest;

import com.project.rest.responses.AlbumCollection;
import com.project.rest.responses.Message;
import com.project.rest.responses.WebError;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import impl.factory.AlbumDBRepoFactory;
import lib.exception.RepException;
import lib.models.Album;
import lib.repos.db.AlbumRepo;
import lib.web.JSONifiable;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.charset.StandardCharsets;
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
//
//    @GET
//    @Path("/upload/{isrc}")
//    public void getFile(@PathParam("isrc") String isrc){
//
//    }

//    @POST
//    @Path("/upload/{isrc}")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response uploadFile(@Context HttpHeaders headers, @FormDataParam("coverImage") InputStream inputStream, @PathParam("isrc") String isrc){
//        try {
//            //byte[] binary = readFile(inputStream);
//            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//            int nRead;
//            byte[] data = new byte[1024];
//            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
//                buffer.write(data, 0, nRead);
//            }
//
//            buffer.flush();
//            buffer.close();
//            byte[] binary = buffer.toByteArray();
//           // String str = new String(binary, "Cp1252");
//           // str = str.substring(str.indexOf("\r\n\r\n"),str.lastIndexOf("------"));
//            //binary = str.trim().getBytes("Cp1252");
//
//            String mime = ".jpg";
//            Album a = new Album();
//            a.setIsrc(isrc);
//            a.setCoverImage(binary);
//            a.setMimeType(mime);
//            this.repo.editImage(a);
//            return successOperation("Uploaded image for " + isrc);
//
//        }catch (Exception e) {
//            return errorResponse(e);
//        }
//    }
//
    @GET
    @Path("/download/{isrc}")
    public Response downloadFile(@Context HttpHeaders headers, @PathParam("isrc") String isrc){
        Album album;

        try {
            album = repo.read(isrc);
            try (FileOutputStream stream = new FileOutputStream("temp/"+ album.getTitle() + album.getMimeType())) {
                stream.write(album.getCoverImage());
            }
        } catch (Exception e) {
            return errorResponse(e);
        }

        File file = new File("temp/"+ album.getTitle() + album.getMimeType());
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition","attachment; filename=\"" + album.getTitle() + album.getMimeType() +"\"");
        return response.build();
    }

    @DELETE
    @Path("/img/{isrc}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeFile(@PathParam("isrc") String isrc){
        try {
            Album a = new Album();
            a.setIsrc(isrc);
            repo.editImage(a);
            return successOperation("Successfully Deleted image for " + isrc);
        }catch (Exception e){
            return errorResponse(e);
        }
    }


    public static Response errorResponse(Exception e){
        WebError error = new WebError("RepException", "Failed Operation: \n\n" + e.getMessage());
        return Response.status(200).entity(error).build();
    }

    private Response albumCollectionResponse(List<Album> a){
        return Response.status(200).entity(new AlbumCollection(a)).build();
    }

    private Response albumResponse(Album a){
        return Response.status(200).entity(a).build();
    }

    public static Response successOperation(String mes){
        return Response.status(200).entity(new Message(mes)).build();
    }

    private byte[] readFile(InputStream inputStream) throws IOException {
        return new byte[inputStream.available()];
    }

}
