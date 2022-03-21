package controller;

import model.Artist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ArtistController {

  private Connection connection;
  private EntityManagerFactory entityManagerFactory;

  public ArtistController(Connection connection) {
    this.connection = connection;
  }

  public ArtistController(Connection connection, EntityManagerFactory entityManagerFactory) {
    this.connection = connection;
    this.entityManagerFactory = entityManagerFactory;
  }

  public List<Artist> readArtistsFile(String filename) throws IOException {
    int id;
    String firstname, lastname;
    Date born, died;
    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
    List<Artist> artistsList = new ArrayList<Artist>();

    BufferedReader br = new BufferedReader(new FileReader(filename));
    String linea = "";
    while ((linea = br.readLine()) != null) {
      StringTokenizer str = new StringTokenizer(linea, ",");
      id = Integer.parseInt(str.nextToken());
      firstname = str.nextToken();
      lastname = str.nextToken();

      try {
        born = dateFormat.parse(str.nextToken());
        died = dateFormat.parse(str.nextToken());

        artistsList.add(new Artist(id, firstname, lastname, born, died));

      } catch (ParseException e) {

        e.printStackTrace();
      }
    }
    br.close();

    return artistsList;
  }

  public void printArtists(ArrayList<Artist> artistsList) {
    for (int i = 0; i < artistsList.size(); i++) {
      System.out.println(artistsList.get(i).toString());
    }
  }


  /* Method to CREATE an Artist in the database */
  public void addArtist(Artist artist) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Artist artistExists = (Artist) em.find(Artist.class, artist.getArtistId());
    if (artistExists == null ){
      System.out.println("insert artist");
      em.persist(artist);
    }
    em.getTransaction().commit();
    em.close();
  }


  /* Method to READ all Artists */
  public void listArtists() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    List<Artist> result = em.createQuery("from Artist", Artist.class)
        .getResultList();
    for (Artist artist : result) {
      System.out.println(artist.toString());
    }
    em.getTransaction().commit();
    em.close();
  }

  /* Method to UPDATE activity for an artist */
  public void updateArtist(Integer artistId, String firstname, String lastname, Date born, Date died) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Artist artist = (Artist) em.find(Artist.class, artistId);
    artist.setFirstname(firstname);
    artist.setLastname(lastname);
    artist.setBorn(born);
    artist.setDied(died);
    em.merge(artist);
    em.getTransaction().commit();
    em.close();
  }

  /* Method to DELETE an Artist from the records */
  public void deleteArtist(Integer artistId) {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    Artist artist = (Artist) em.find(Artist.class, artistId);
    em.remove(artist);
    em.getTransaction().commit();
    em.close();
  }

}
