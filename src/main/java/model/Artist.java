package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)
@Table(name = "artist")
public class Artist implements Serializable {
  @Id
  @Column(name = "id_artist")
  int artistId;
  @Column(name = "firstname", length = 30)
  String firstname;
  @Column(name = "lastname", length = 30)
  String lastname;
  @Column(name = "born", length = 12)
  Date born;
  @Column(name = "died", length = 4)
  Date died;

  public Artist(int artistId, String firstname, String lastname, Date born,
                Date died) {
    super();
    this.died = died;
    this.firstname = firstname;
    this.lastname = lastname;
    this.born = born;
    this.artistId = artistId;
  }

  public Artist() {

  }

  public int getArtistId() {
    return artistId;
  }

  public void setArtistId(int artistId) {
    this.artistId = artistId;
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

  public Date getBorn() {
    return born;
  }

  public void setBorn(Date born) {
    this.born = born;
  }

  public Date getDied() {
    return died;
  }

  public void setDied(Date died) {
    this.died = died;
  }

  @Override
  public String toString() {
    return "Artist [id_artist=" + artistId + ", firstname=" + firstname + ", lastname=" + lastname
        + ", born=" + born + ", died=" + died
        + "]";
  }


}
