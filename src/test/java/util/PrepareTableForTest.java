package util;

import connection.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class PrepareTableForTest {

    private static final String CREATE_DIRECTOR_TABLE = """
            CREATE TABLE director
            (
                id_director BIGSERIAL PRIMARY KEY NOT NULL,
                name_director VARCHAR(128) UNIQUE NOT NULL ,
                birth_date_director DATE NOT NULL 
            );
            
            """;
    private static final String CREATE_FILM_TABLE = """
            CREATE TABLE film
            (
                id_film BIGSERIAL PRIMARY KEY NOT NULL ,
                name_film VARCHAR(128) UNIQUE NOT NULL ,
                country VARCHAR(128) NOT NULL ,
                date_realize DATE NOT NULL ,
                genre VARCHAR(32) NOT NULL ,
                id_director BIGINT REFERENCES director(id_director) ON DELETE CASCADE NOT NULL 
            );
            """;

    private static final String DROP_TABLES = """
            DROP TABLE film;
            DROP TABLE director;
            """;

    private static final String INSERT_DIRECTORS = """
            INSERT INTO director (name_director, birth_date_director)
            VALUES ('Chris Columbus', '1958-09-10'),
                   ('Peter Jackson', '1961-10-30'),
                   ('Quentin Tarantino', '1963-03-27');
            """;

    private static final String INSERT_FILMS = """
            INSERT INTO film (name_film, country, date_realize, genre, id_director)
            VALUES ('Kill Bill: Vol. 1', 'USA', '2003-09-29', 'ACTION', (SELECT director.id_director FROM director WHERE name_director = 'Quentin Tarantino')),
            ('Kill Bill: Vol. 2', 'USA', '2004-04-08', 'ACTION', (SELECT director.id_director FROM director WHERE name_director = 'Quentin Tarantino')),
            ('Harry Potter and the Sorcerer''s Stone', 'UK', '2004-04-08', 'FANTASY', (SELECT director.id_director FROM director WHERE name_director = 'Chris Columbus')),
            ('The Lord of the Rings: The Fellowship of the Ring', 'NEW_ZEALAND', '2001-12-21', 'FANTASY', (SELECT director.id_director FROM director WHERE name_director = 'Peter Jackson'));
            
            
            """;
    private static final String TRUNCATE_TABLES = """
            TRUNCATE TABLE director CASCADE;
            TRUNCATE TABLE film;
            """;

    public static void createDirectorTable(DataSource dataSource) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_DIRECTOR_TABLE)) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createFilmTable(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FILM_TABLE)) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dropTables(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLES)) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertFilms(DataSource dataSource) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FILMS)) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void insertDirectors(DataSource dataSource) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DIRECTORS)) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void truncateTables(DataSource dataSource) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TRUNCATE_TABLES)) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
