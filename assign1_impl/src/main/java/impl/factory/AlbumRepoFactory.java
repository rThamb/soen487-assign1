package impl.factory;

import impl.dao.AlbumDAO;
import lib.repos.AlbumRepo;

public class AlbumRepoFactory {

    private static AlbumRepo repo;

    private AlbumRepoFactory(){}

    public static synchronized AlbumRepo getInstance(){
        if(repo == null){
            repo = new AlbumDAO();
        }
        return repo;
    }
}

