package impl.dao;

import lib.models.Album;
import lib.repos.AlbumRepo;

import java.util.ArrayList;
import java.util.List;

public class AlbumDAO implements AlbumRepo {

    private List<Album> albums;

    public AlbumDAO(){
        albums = new ArrayList<>();
    }

    private List<Album> copyList(){
        List<Album> copy = new ArrayList<>();
        this.albums.forEach(ele -> copy.add(new Album(ele)));
        return copy;
    }

    @Override
    public List<Album> readAll() {
        return albums;
    }

    @Override
    public Album read(String isrc) {
        Album album = this.albums.stream().filter(ele -> ele.getIsrc().equalsIgnoreCase(isrc))
                .findFirst()
                .orElse(null);

        if(album != null){
            album = new Album(album);
        }

        return album;
    }

    @Override
    public synchronized void add(Album a) {
        if(a != null)
            this.albums.add(a);
    }

    @Override
    public synchronized void update(Album a) {

        //remove based on ID
        this.albums.remove(a);

        //add new entry
        this.albums.add(a);
    }

    @Override
    public synchronized void delete(String isrc) {
        Album a = new Album();
        a.setIsrc(isrc);
        this.albums.remove(a);
    }
}

