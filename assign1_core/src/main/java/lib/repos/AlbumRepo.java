package lib.repos;

import lib.models.Album;

import java.util.List;

public interface AlbumRepo {

    List<Album> readAll();

    Album read(String isrc);

    void add(Album a);

    void update(Album a);

    void delete(String isrc);

    void editImage(Album a) throws Exception;

}