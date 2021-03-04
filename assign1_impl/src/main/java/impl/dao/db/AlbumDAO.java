package impl.dao.db;

import impl.database.DBConnection;
import lib.logs.LogEntry;
import lib.models.Album;
import lib.models.Artist;
import lib.repos.LogRepo;
import lib.repos.db.AlbumRepo;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;



public class AlbumDAO implements AlbumRepo, LogRepo{

    private DBConnection connectionCenter;
    private static String ADD_TAG = "CREATE";
    private static String UPDATE_TAG = "UPDATE";
    private static String DELETE_TAG = "DELETE";


    public AlbumDAO(Properties properties){
        connectionCenter = new DBConnection(properties);
    }

    public List<Album> readAll() throws SQLException {

        List<Album> albums = new ArrayList<Album>();
        String query = "SELECT * FROM t_album";
        try(Connection con = getConnection();) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                Album a = parseAlbumRow(rs);
                albums.add(a);
            }
        }

        return albums;
    }

    public Album read(String isrc) throws SQLException {
        Album a = null;
        String query = "SELECT * FROM t_album WHERE isrc=?";

        try(Connection con = getConnection();) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,isrc);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                a = parseAlbumRow(rs);
            }
        }
        return a;
    }

    private Album parseAlbumRow(ResultSet rs) throws SQLException {
        String isrc = rs.getString("isrc");
        String title = rs.getString("title");
        String des = rs.getString("description");

        String yearStr = rs.getString("year_released");
        int year = 0;
        if(yearStr != null && !yearStr.equals("")){
            year = Integer.parseInt(yearStr);
        };

        String artist_fname = rs.getString("artist_first_name");
        String artist_lname =rs.getString("artist_last_name");
        Artist artist = new Artist();
        artist.setFirstname(artist_fname);
        artist.setLastname(artist_lname);
        byte[] coverImage = rs.getBytes("cover");
        String media_type = rs.getString("media_type");

        Album a = new Album();
        a.setIsrc(isrc);
        a.setTitle(title);
        a.setDescription(des);
        a.setYear(year);
        a.setArtist(artist);
        a.setCoverImage(coverImage);
        a.setMimeType(media_type);
        return a;
    }

    public void add(Album a) throws SQLException {
        String query = "INSERT INTO t_album(isrc, title, description, year_released, artist_first_name, artist_last_name, cover, media_type) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection con = getConnection();) {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, a.getIsrc());
            ps.setString(2, a.getTitle());
            ps.setString(3, a.getDescription());
            ps.setString(4, a.getYear() + "");
            ps.setString(5, a.getArtist().getFirstname());
            ps.setString(6, a.getArtist().getLastname());
            ps.setBytes(7, a.getCoverImage());
            ps.setString(8, a.getMimeType());

            int i = ps.executeUpdate();

            if (i == 1) {
                System.out.println("Successful: Added new album");
            } else
                throw new SQLException("No ROWS were Affected.");

            enterLog(a.getIsrc(), ADD_TAG);

        }
    }

    public void update(Album a) throws SQLException {
        String query = "UPDATE t_album\n" +
                "SET title=?, description=?, year_released=?, artist_first_name=?, artist_last_name=? \n" +
                "WHERE isrc = ?";

        try(Connection con = getConnection();) {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,a.getTitle());
            ps.setString(2,a.getDescription());
            ps.setString(3,a.getYear() + "");
            ps.setString(4,a.getArtist().getFirstname());
            ps.setString(5,a.getArtist().getLastname());
            ps.setString(6,a.getIsrc());

            int i = ps.executeUpdate();

            if(i == 1) {
                System.out.println("Successful: Updated album");
            }else
                throw new SQLException("No ROWS were Affected.");
        }

        enterLog(a.getIsrc(), UPDATE_TAG);
    }

    public void delete(String isrc) throws SQLException {

        String query = "DELETE FROM t_album WHERE isrc = ?";

        try(Connection con = getConnection();) {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,isrc);

            int i = ps.executeUpdate();

            if(i == 1) {
                System.out.println("Successful: Deleted album");
            }else
                throw new SQLException("No ROWS were Affected.");
        }
        enterLog(isrc, DELETE_TAG);
    }

    @Override
    public void editImage(Album album) throws SQLException {

        String query = "UPDATE t_album\n" +
                "SET cover=?, media_type=? \n" +
                "WHERE isrc = ?";

        try(Connection con = getConnection();) {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setBytes(1, album.getCoverImage());
            ps.setString(2, album.getMimeType());
            ps.setString(3, album.getIsrc());

            int i = ps.executeUpdate();

            if(i == 1) {
                System.out.println("Successful: Updated album");
            }else
                throw new SQLException("No ROWS were Affected.");
        }
        enterLog(album.getIsrc(), UPDATE_TAG);
    }


    public List<LogEntry> readLogs() throws SQLException {

        List<LogEntry> logs = new ArrayList<LogEntry>();

        String query = "SELECT * FROM t_logentry";
        try(Connection con = getConnection();) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {

                String type = rs.getString("change_type");
                String isrc =rs.getString("isrc");
                Date time = rs.getDate("time_stamp");
                LogEntry logEntry = new LogEntry();
                logEntry.setType(type);
                logEntry.setIsrc(isrc);
                logEntry.setTimestamp(time);
                logs.add(logEntry);
            }
        }
        return logs;
    }


    public void clearLogs() throws SQLException {

        String query = "DELETE FROM t_logentry";
        try(Connection con = getConnection();) {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            int i = ps.executeUpdate();

            if (i == 1) {
                System.out.println("Successful: Cleared Logs");
            } else
                throw new SQLException("No ROWS were Affected.");
        }
    }

    private void enterLog(String isrc, String type) throws SQLException {
        LogEntry log = new LogEntry();
        log.setIsrc(isrc);
        log.setType(type);
        Date d = new Date(Calendar.getInstance().getTime().getTime());
        log.setTimestamp(d);

        String query = "INSERT INTO t_logentry(change_type, isrc, time_stamp) " +
                "VALUES(?, ?, ?)";

        try(Connection con = getConnection();) {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, log.getType());
            ps.setString(2, log.getIsrc());
            ps.setDate(3, new Date(log.getTimestamp().getTime()));

            int i = ps.executeUpdate();

            if (i == 1) {
                System.out.println("Successful: Added Log");
            } else
                throw new SQLException("No ROWS were Affected.");

        }

    }

    private Connection getConnection(){
        return connectionCenter.getConnection();
    }
}
