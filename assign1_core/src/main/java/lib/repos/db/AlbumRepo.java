package lib.repos.db;

import lib.models.Album;

import java.util.List;

public interface AlbumRepo {

    List<Album> readAll() throws Exception;

    Album read(String isrc) throws Exception;

    void add(Album a) throws Exception;

    void update(Album a) throws Exception;

    void delete(String isrc) throws Exception;
}