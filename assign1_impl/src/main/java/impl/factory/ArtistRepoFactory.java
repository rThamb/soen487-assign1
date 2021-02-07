package impl.factory;

import impl.dao.ArtistDAO;
import lib.repos.ArtistRepo;

public class ArtistRepoFactory {

    private static ArtistRepo repo;

    private ArtistRepoFactory(){}

    public static synchronized ArtistRepo getInstance(){
        if(repo == null){
            repo = new ArtistDAO();
        }
        return repo;
    }
}
