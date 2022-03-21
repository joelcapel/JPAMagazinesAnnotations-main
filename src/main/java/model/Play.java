package model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Access(AccessType.FIELD)
@Table(name = "play")
public class Play implements Serializable {
    @Id
    @Column(name = "id_song")
    Song song;
    @Id
    @Column(name = "id_artist")
    Artist artist;
    @Id
    @Column(name = "instrument")
    String instrument;

    public Play() {
    }

    public Play(Song song, Artist artist, String instrument) {
        super();
        this.song = song;
        this.artist = artist;
        this.instrument = instrument;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    @Override
    public String toString() {
        return "Play{" +
                "song=" + song +
                ", artist=" + artist +
                ", instrument='" + instrument + '\'' +
                '}';
    }
}
