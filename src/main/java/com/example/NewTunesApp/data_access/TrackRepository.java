package com.example.NewTunesApp.data_access;

import com.example.NewTunesApp.models.Track;
import com.example.NewTunesApp.logger.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TrackRepository {

    private Logger logger = new Logger();

    private String URL = ConnectionHelper.CONNECTION_URL;
    private Connection conn = null;

    //Get five random songs
    public ArrayList<String> getRandomTrack() {
        ArrayList<String> songs = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            logger.log("Connection to SQLite has been established.");
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT Track.Name FROM Track ORDER BY RANDOM() LIMIT 5");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                songs.add(
                        resultSet.getString("Name")
                );
            }
            logger.log("Select five random tracks successful");
        } catch (Exception exception) {
            logger.log(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                logger.log(exception.toString());
            }
        }
        return songs;
    }

    //Get five random artists
    public ArrayList<String> getRandomArtist() {
        ArrayList<String> artist = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            logger.log("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT Artist.Name FROM Artist ORDER BY RANDOM() LIMIT 5");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                artist.add(
                        resultSet.getString("Name")
                );
            }
            logger.log("Select five random artists successful");
        } catch (Exception exception) {
            logger.log(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                logger.log(exception.toString());
            }
        }
        return artist;
    }

    //Get five random genres
    public ArrayList<String> getRandomGenre() {
        ArrayList<String> genre = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            logger.log("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT Genre.Name FROM Genre ORDER BY RANDOM() LIMIT 5");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                genre.add(
                        resultSet.getString("Name")
                );
            }
            logger.log("Select five random genres successful");
        } catch (Exception exception) {
            logger.log(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                logger.log(exception.toString());
            }
        }
        return genre;
    }

    //Get all tracks that contain the searched word
    public ArrayList<Track> getTrack(String input) {
        input = input.toLowerCase();
        ArrayList<Track> tracks = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            logger.log("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT Track.Name as tName, Genre.Name as gname, Album.Title, Artist.Name as aName\n" +
                            "FROM Track\n" +
                            "JOIN Genre ON Track.GenreId = Genre.GenreId\n" +
                            "JOIN Album ON Track.AlbumId = Album.AlbumId\n" +
                            "JOIN Artist ON Album.ArtistId = Artist.ArtistId\n" +
                            "WHERE Track.Name LIKE ?");
            preparedStatement.setString(1, "%" + input + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tracks.add( new Track(
                    resultSet.getString("aName"),
                    resultSet.getString("tName"),
                    resultSet.getString("gName"),
                    resultSet.getString("title")
                        )
                );
            }
            logger.log("Select matching songs successful");
        } catch (Exception exception) {
            logger.log(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                logger.log(exception.toString());
            }
        }
        return tracks;
    }
}