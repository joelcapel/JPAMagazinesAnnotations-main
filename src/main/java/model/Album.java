package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@Table(name = "albums")
public class Album implements Serializable {
  @Id
  @Column(name = "id_album")
  private int albumId;
  @Column(name = "title", length = 30)
  private String title;
  @Column(name = "recorded")
  private Date recorded;
  @Column(name = "label")
  private String label;
  @Column(name = "available")
  private boolean available;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_album", referencedColumnName = "id_album")
  private List<Song> songs = new ArrayList<Song>();

  public Album(int albumId, String title, Date recorded, String label, boolean available) {
    super();
    this.title = title;
    this.recorded = recorded;
    this.label = label;
    this.available = available;
    this.albumId = albumId;
  }

  public Album() {
    super();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getRecorded() {
    return recorded;
  }

  public void setRecorded(Date recorded) {
    this.recorded = recorded;
  }

  public int getAlbumId() {
    return albumId;
  }

  public void setAlbumId(int albumId) {
    this.albumId = albumId;
  }

  public void addSong(Song sng) {
    songs.add(sng);
  }

  public Song getSong(int i) {
    return songs.get(i);
  }

  public List<Song> getSongs() {
    return songs;
  }

  public void setSongs(List<Song> songs) {
    this.songs = songs;
  }


  @Override
  public String toString() {
    String result = "Album [id_album=" + albumId + ",title=" + title + ", recorded="
        + recorded.toString() + ",label=" + label + ",available=" + available + "]";

    result += "\n Llista de songs: [ \n";

    for (Song s : songs) {
      result += "\t";
      result += s.toString();
      result += "\n";
    }

    result += "] \n";

    return result;
  }

}
