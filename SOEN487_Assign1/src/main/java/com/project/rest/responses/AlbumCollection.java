package com.project.rest.responses;

import lib.models.Album;
import lib.web.JSONifiable;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class AlbumCollection implements JSONifiable, Serializable {

    private List<Album> albums;

    public AlbumCollection(){}

    public AlbumCollection(List<Album> albums) {
        this.albums = albums;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
