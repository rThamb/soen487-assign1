package lib.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Artist implements Serializable {

    private String nickname;
    private String firstname;
    private String lastname;
    private String bio;

    public Artist(){}

    public Artist(Artist a){
        this.nickname = a.nickname;
        this.firstname = a.firstname;
        this.lastname = a.lastname;
        this.bio = a.bio;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o == null || getClass() != o.getClass())) {
            Artist artist = (Artist) o;
            return nickname.equals(artist.nickname);
        }
        else
            return false;
    }


    @Override
    public String toString(){
        return String.format("{\n\tnickname: %s,\n\tfirst: %s,\n\tlast: %s,\n\tbio: %s\n}", this.nickname, this.firstname, this.lastname, this.bio);
    }

}
