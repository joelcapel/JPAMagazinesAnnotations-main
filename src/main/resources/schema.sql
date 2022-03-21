CREATE TABLE artists
(
  id_artist integer NOT NULL,
  firstname character varying(30) NOT NULL,
  lastname character varying(30) NOT NULL,
  born date NOT NULL,
  died date NOT NULL,
  CONSTRAINT pk_artists PRIMARY KEY (id_artist)
);

CREATE TABLE albums
(
  id_album serial NOT NULL,
  title character varying(30) NOT NULL,
  recorded date NOT NULL ,
  title character varying(30) NOT NULL,
  available boolean NOT NULL,
  CONSTRAINT pk_albums PRIMARY KEY (id_album),
  CONSTRAINT uk_title UNIQUE (title)
);


CREATE TABLE songs
(
  id_song serial NOT NULL,
  id_album integer,
  id_artist integer NOT NULL,
  title character varying(30) NOT NULL,
  duration integer ,
  CONSTRAINT pk_songs PRIMARY KEY (id_song),
  CONSTRAINT fk_sng_songs FOREIGN KEY (id_song)
      REFERENCES songs (id_song) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_songs_artists FOREIGN KEY (id_artist)
      REFERENCES artists (id_artist) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_songs UNIQUE (title)
);

