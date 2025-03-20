package dao;

import connection.DataSource;
import entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static dao.SqlForFilm.*;

public class FilmDao implements Dao<Long, Film> {


    private DataSource dataSource;


    public FilmDao() {
        this.dataSource = new DataSource();
    }

    public FilmDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Film create(Film object) {


        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL.getQuery(), Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setObject(1, object.getNameFilm());
            preparedStatement.setObject(2, object.getCountry().name());
            preparedStatement.setObject(3, object.getDateRealize());
            preparedStatement.setObject(4, object.getGenre().name());
            preparedStatement.setObject(5, object.getDirector().getId());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            object.setId(resultSet.getObject("id_film", Long.class));

            return object;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Film> update(Film object) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL.getQuery())) {

            preparedStatement.setObject(1, object.getNameFilm());
            preparedStatement.setObject(2, object.getCountry().name());
            preparedStatement.setObject(3, object.getDateRealize());
            preparedStatement.setObject(4, object.getGenre().name());
            preparedStatement.setObject(5, object.getDirector().getId());
            preparedStatement.setObject(6, object.getId());

            preparedStatement.executeUpdate();

            return findById(object.getId());

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL.getQuery())) {

            preparedStatement.setLong(1, id);

            int update = preparedStatement.executeUpdate();

            return update > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Film> findById(Long id) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID.getQuery())) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            Film film = null;

            if (resultSet.next()) {
                film = buildFilm(resultSet);

                Director director = buildDirector(resultSet);
                director.addFilm(film);
                film.setDirector(director);
            }

            return Optional.ofNullable(film);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Set<Film> findAll() {

        Set<Film> films = new HashSet<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatementFilms = connection.prepareStatement(FIND_ALL_SQL.getQuery())) {

            ResultSet resultSetFilms = preparedStatementFilms.executeQuery();


            while (resultSetFilms.next()) {
                Director director = buildDirector(resultSetFilms);
                Film film = buildFilm(resultSetFilms);

                director.addFilm(film);
                film.setDirector(director);

                films.add(film);
            }

            return films;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Film buildFilm(ResultSet resultSetFilms) throws SQLException {
        return new Film(
                resultSetFilms.getLong("id_film"),
                resultSetFilms.getString("name_film"),
                Country.valueOf(resultSetFilms.getString("country")),
                LocalDate.parse(resultSetFilms.getString("date_realize"), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                Genre.valueOf(resultSetFilms.getString("genre")),
                null
        );
    }

    private Director buildDirector(ResultSet resultSetFilms) throws SQLException {
        return new Director(
                resultSetFilms.getLong("id_director"),
                resultSetFilms.getString("name_director"),
                LocalDate.parse(resultSetFilms.getString("birth_date_director"), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                new ArrayList<>()
        );
    }

}
