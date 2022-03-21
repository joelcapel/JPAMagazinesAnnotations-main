package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Access(AccessType.FIELD)
@Table(name = "songs")
public class Song implements Serializable {
  @Id
  @Column(name = "id_song")
  int songId;
  @Column(name = "title", length = 30)
  String title;
  @Column(name = "duration")
  int duration;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "id_artist")
  public Artist artist;

  public Song(int songId, String title, int duration,
                 Artist artist) {
    super();
    this.songId = songId;
    this.title = title;
    this.duration = duration;
    this.artist = artist;
  }

  public Song() {
    super();

  }

  public int getSongId() {
    return songId;
  }

  public void setSongId(int songId) {
    this.songId = songId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public Artist getArtist() {
    return artist;
  }

  public void setArtist(Artist artist) {
    this.artist = artist;
  }

  @Override
  public String toString() {
    return "Song{" +
        "songId=" + songId +
        ", title='" + title + '\'' +
        ", duration=" + duration +
        ", artist=" + artist.toString() +
        '}';
  }
}
