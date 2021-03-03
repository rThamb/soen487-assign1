package impl.dao.db;

import impl.database.DBConnection;
import lib.logs.LogEntry;
import lib.models.Album;
import lib.repos.AlbumRepo;
import lib.repos.LogRepo;

import java.util.List;
import java.util.Properties;

public class AlbumDAO implements AlbumRepo, LogRepo {

    private DBConnection connectionCenter;


    public AlbumDAO(Properties properties){
        connectionCenter = new DBConnection(properties);
    }

    @Override
    public List<Album> readAll() {
        return null;
    }

    @Override
    public Album read(String s) {
        return null;
    }

    @Override
    public void add(Album album) {

    }

    @Override
    public void update(Album album) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public List<LogEntry> readLogs() {
        return null;
    }

    @Override
    public void clearLogs() {

    }
}
