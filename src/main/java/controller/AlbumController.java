package controller;

import model.Album;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AlbumController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;

  public AlbumController(Connection connection) {
    this.connection = connection;
  }

  public AlbumController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
  }

  /**
   * @param filename Aquest String correspon amb l'arxiu on s'emmagatzemen les
   *                 dades de les instancies de Revista
   * @param s
   * @param s1
   * @throws IOException <dt><b>Preconditions:</b>
   *                     <dd>
   *                     filename<>nil </br> llistaRevistes == nil
   *                     <dt><b>Postconditions:</b>
   *                     <dd>
   *                     llistaRevistes<>nil
   */

  public List<Album> readAlbumsFile(String filename, String s, String s1) throws IOException {
    int albumId;
    String title, label;
    Date recorded;
    boolean available;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    List<Album> albumsList = new ArrayList();

    BufferedReader br = new BufferedReader(new FileReader(filename));
    String linea = "";
    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, ",");
      albumId = Integer.parseInt(str.nextToken());
      title = str.nextToken();

      try {
        recorded = dateFormat.parse(str.nextToken());
        label = str.nextToken();
        available = Boolean.parseBoolean(str.nextToken());
        albumsList.add(new Album(albumId, title, recorded, label, available));

      } catch (ParseException e) {
        System.err.println("Errada format data al fitxer");
        e.printStackTrace();
      }

    }
    br.close();
    return albumsList;
  }

  public void printAlbums(ArrayList<Album> albumsList) {
    for (int i = 0; i < albumsList.size(); i++) {
      System.out.println(albumsList.get(i).toString());
    }
  }

  /* Method to CREATE a Album  in the database */
  public void addAlbum(Album album) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.merge(album);

    em.getTransaction().commit();
    em.close();
  }

  /* Method to READ all Album */
  public void listAlbums() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Album> result = em.createQuery("from Album", Album.class)
        .getResultList();



    for (Album album : result) {
      System.out.println(album.toString());
    }
    em.getTransaction().commit();
    em.close();
  }

  /* Method to UPDATE activity for an Album */
  public void updateAlbum(Integer albumId) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Album album = (Album) em.find(Album.class, albumId);
    em.merge(album);
    em.getTransaction().commit();
    em.close();
  }

  /* Method to DELETE an AlbumZ from the records */
  public void deleteArtist(Integer albumId) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Album album = (Album) em.find(Album.class, albumId);
    em.remove(album);
    em.getTransaction().commit();
    em.close();
  }
}
