package lib.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Album implements Serializable {

    private String isrc;
    private String title;
    private String description;
    private int year;
    private Artist artist;

    public Album(){}

    public Album(Album a){
        this.isrc = a.isrc;
        this.title = a.title;
        this.description = a.description;
        this.year = a.year;
        if(a.artist != null)
            this.artist = new Artist(a.artist);
    }

    public String getIsrc() {
        return isrc;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o == null || getClass() != o.getClass())) {
            Album album = (Album) o;
            return isrc.equals(album.isrc);
        }
        else
            return false;
    }

    @Override
    public String toString(){
        return String.format("{\n\tisrc: %s,\n\ttitle:%s,\n\tyear: %s,\n\t desc: %s\n}",
                this.isrc, this.title, this.year + "", this.description);
    }
}
