package controller;

import model.Artist;

import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class PlayController {

    private Connection connection;
    private EntityManagerFactory entityManagerFactory;

    private SongController songController = new SongController(connection);
    private ArtistController artistController = new ArtistController(connection);

    public void playController(Connection connection) {
        this.connection = connection;
    }

    public void playController(Connection connection, EntityManagerFactory entityManagerFactory) {
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
}
