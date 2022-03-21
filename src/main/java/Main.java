import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import controller.AlbumController;
import controller.ArtistController;
import controller.SongController;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import database.ConnectionFactory;
import view.Menu1;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Main {

  static SessionFactory sessionFactoryObj;


  private static SessionFactory buildSessionFactory() {
    try {
      StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
          .configure("hibernate.cfg.xml").build();
      Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
      return metadata.getSessionFactoryBuilder().build();

    } catch (HibernateException he) {
      System.out.println("Session Factory creation failure");
      throw he;
    }
  }

  public static EntityManagerFactory createEntityManagerFactory(){
    EntityManagerFactory emf;
    try {
      emf = Persistence.createEntityManagerFactory("JPAMagazines");
    } catch (Throwable ex) {
      System.err.println("Failed to create EntityManagerFactory object."+ ex);
      throw new ExceptionInInitializerError(ex);
    }
    return emf;
  }

  public static void main(String[] args) {
    ArrayList<Album> albums1 = new ArrayList();

    ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    Connection c = connectionFactory.connect();

//    SessionFactory sessionFactory = buildSessionFactory();
    EntityManagerFactory entityManagerFactory = createEntityManagerFactory();
    //sessionObj = buildSessionFactory().openSession();


    ArtistController artistController = new ArtistController(c, entityManagerFactory);
    SongController songController = new SongController(c, entityManagerFactory);
    AlbumController albumController = new AlbumController(c, entityManagerFactory);

    Menu1 menu = new Menu1();
    int opcio;
    opcio = menu.mainMenu();

    switch (opcio) {

      case 1:

        System.out.println("1!!");
        try {

          List<Artist> artists = artistController.readArtistsFile("src/main/resources/artists.txt");
          List<Album> albums = songController.readSongsFile("src/main/resources/songs.txt", "src/main/resources/albums.txt", "src/main/resources/songs.txt");
          List<Song> songs = songController.readSongsFile("src/main/resources/songs.txt", "src/main/resources/artistes.txt");

          System.out.println("Revistes llegides des del fitxer");
          for (int i = 0; i < albums.size(); i++) {
            System.out.println(albums.get(i).toString()+"\n");
            for (int j = 0; j < albums.get(i).getSongs().size(); j++) {
              Artist artist = albums.get(i).getSongs().get(j).getArtist();
              artistController.addArtist(artist);

              System.out.println("EL ARTIST:");
              System.out.println(artist);

              Song song = albums.get(i).getSongs().get(j);
              song.setArtist(artist);

              System.out.println("LA SONG:");
              System.out.println(song);

              songController.addSong(song);
            }

            albumController.addAlbum(albums.get(i));
          }

        } catch (NumberFormatException | IOException e) {

          e.printStackTrace();
        }
        break;

      default:
        System.out.println("Adeu!!");
        System.exit(1);
        break;

    }
  }
}