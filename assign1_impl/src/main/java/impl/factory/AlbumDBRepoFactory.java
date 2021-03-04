package impl.factory;

import impl.dao.db.AlbumDAO;
import lib.repos.db.AlbumRepo;

import java.util.Properties;

public class AlbumDBRepoFactory {

    private static AlbumRepo repo;

    private AlbumDBRepoFactory(){}

    public static synchronized AlbumRepo getInstance(Properties prop){
        if(repo == null){
            repo = new AlbumDAO(prop);
        }
        return repo;
    }
}
