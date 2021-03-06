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


@Path("cover")
public class CoverController {

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

    @POST
    @Path("/upload/{isrc}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(@Context HttpHeaders headers, @FormDataParam("coverImage") InputStream inputStream, @PathParam("isrc") String isrc){
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
            buffer.close();

            byte[] binary = buffer.toByteArray();
            String str = new String(binary);
            int filenameIndex = str.indexOf("filename=\"");
            String filename = str.substring(filenameIndex + 10 ,str.indexOf("\"", filenameIndex + 10));
            String mimeType = filename.substring(filename.indexOf("."));
            String header = str.substring(0, str.indexOf("\r\n\r\n"));
            int headerLength = header.length();
            int last = str.lastIndexOf("--");
            String endStr = str.substring(str.lastIndexOf("--", last-2),last+2);
            int endLength = endStr.length();

            int binSize = binary.length;
            byte[] binaryContent = new byte[binSize-headerLength-endLength];
            int start = headerLength + 4;
            int end = binSize - endLength - 2;
            int counter = 0;

            for (int i = start; i < end; i++) {
                binaryContent[counter] = binary[i];
                counter++;
            }

            Album a = new Album();
            a.setIsrc(isrc);
            a.setCoverImage(binaryContent);
            a.setMimeType(mimeType);
            this.repo.editImage(a);
            return AlbumController.successOperation("Uploaded image for " + isrc);

        }catch (Exception e) {
            return AlbumController.errorResponse(e);
        }
    }
}
