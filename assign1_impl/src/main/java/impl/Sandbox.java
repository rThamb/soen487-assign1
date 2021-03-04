package impl;

import impl.dao.db.AlbumDAO;
import impl.database.DBConnection;
import lib.models.Album;
import lib.models.Artist;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Properties;

public class Sandbox {

    public static void main(String[] args) throws IOException, SQLException {
        InputStream input = new FileInputStream("config/config.properties");


        Properties prop = new Properties();
        prop.load(input);

        AlbumDAO aDB = new AlbumDAO(prop);

        Album a = new Album();
        a.setIsrc("1532");

        a.setYear(2009);
        Artist ar = new Artist();
        ar.setFirstname("Dim");
        ar.setLastname("Spy");
        a.setArtist(ar);
        a.setMimeType(".png");
        aDB.add(a);
        a.setTitle("EDITTED TITLE");
        aDB.update(a);


        //image stuff
        File f = new File("config/config.properties");
        byte[] binary = Files.readAllBytes(f.toPath());
        String mime = "png";

        a.setCoverImage(binary);
        a.setMimeType(mime);
        aDB.editImage(a);

        //edit album
        a.setTitle("EDIT AFTER IMG EXIST");
        aDB.update(a);

        //a.setCoverImage(null);
        //a.setMimeType(null);
        //aDB.editImage(a);

        aDB.readLogs();
        aDB.delete(a.getIsrc());

        return;
    }

}
