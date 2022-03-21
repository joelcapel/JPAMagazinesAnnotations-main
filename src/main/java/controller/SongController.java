package controller;

import model.Album;
import model.Artist;
import model.Song;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;

public class SongController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;

  private AlbumController albumController = new AlbumController(connection);
  private ArtistController artistController = new ArtistController(connection);

  public SongController(Connection connection) {
    this.connection = connection;
  }

  public SongController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
  }

  /**
   * @param songsFile Aquest String correspon amb l'arxiu on s'emmagatzemen les
   *                     dades de les isntancies de Revista
   * @return ArrayList d'objectes Revista, amb els seus articles i la
   * informació de l'autor
   * @throws IOException <dt><b>Preconditions:</b>
   *                     <dd>
   *                     filename<>nil </br> llistaRevistes<>nil </br>
   *                     llistaRevistes.getRevista(i).getArticles()== nil</br>
   *                     <dt><b>Postconditions:</b>
   *                     <dd>
   *                     llistaRevistes.getRevistes()<>nil</br>
   *                     llistaRevistes.getRevista(i).getArticles()<>nil</br>
   *                     llistaRevistes.getRevista(i).getArticle(j)<>nil</br>
   *                     llistaRevistes
   *                     .getRevista(i).getArticle(j).getAutor()<>nil</br>
   */
  public List<Album> readSongsFile(String songsFile, String albumsFile, String artistsFile)
      throws IOException {
    int songId, albumId, artistId;
    String title;
    int duration;

    BufferedReader br = new BufferedReader(new FileReader(songsFile));
    String linea = "";

    List<Album> albumList = albumController.readAlbumsFile(albumsFile, "src/main/resources/albums.txt", "src/main/resources/artistes.txt");
    List<Artist> artistList = artistController.readArtistsFile(artistsFile);

    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, ",");
      songId = Integer.parseInt(str.nextToken());
      albumId = Integer.parseInt(str.nextToken());
      artistId = Integer.parseInt(str.nextToken());
      title = str.nextToken();
      duration = Integer.parseInt(str.nextToken());

      albumList.get(albumId - 1).addSong(new Song(songId, title, duration, artistList.get(artistId - 1)));

    }
    br.close();

    return albumList;
  }

  public List<Song>  readSongsFile(String songsFile, String artistsFile) throws IOException {
    int songId, albumId, artistId;
    String title;
    int duration;

    BufferedReader br = new BufferedReader(new FileReader(songsFile));
    String linea = "";
    List<Artist> artistList = artistController.readArtistsFile(artistsFile);
    List<Song> songList = new ArrayList<Song>();

    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, ",");
      songId = Integer.parseInt(str.nextToken());
      albumId = Integer.parseInt(str.nextToken());
      artistId = Integer.parseInt(str.nextToken());
      title = str.nextToken();
      duration = Integer.parseInt(str.nextToken());

      songList.add(new Song(songId, title, duration, artistList.get(artistId - 1)));


    }
    br.close();
    return songList;

  }

  /* Method to CREATE an Song in the database */
  public void addSong(Song song) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(song);
    em.getTransaction().commit();
    em.close();
  }

  /* Method to READ all Songs */
  public void listSongs() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Song> result = em.createQuery("from Song", Song.class)
        .getResultList();
    for (Song song : result) {
      System.out.println(song.toString());
    }
    em.getTransaction().commit();
    em.close();
  }

  /* Method to UPDATE activity for an Song */
  public void updateSong(Integer songId) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Song song = (Song) em.find(Song.class, songId);
    em.merge(song);
    em.getTransaction().commit();
    em.close();
  }

  /* Method to DELETE an Song from the records */
  public void deleteSong(Integer songId) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Song song = (Song) em.find(Song.class, songId);
    em.remove(song);
    em.getTransaction().commit();
    em.close();
  }


}
