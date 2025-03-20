package dao;

import connection.DataSource;
import entity.Country;
import entity.Director;
import entity.Film;
import entity.Genre;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static dao.SqlForDirector.*;

public class DirectorDao implements Dao<Long, Director> {

    private DataSource dataSource;

    private static final String DIRECTOR_ID = "id_director";

    public DirectorDao() {
        this.dataSource = new DataSource();
    }

    public DirectorDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Director create(Director object) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL.getQuery(),
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setObject(1, object.getName());
            preparedStatement.setObject(2, object.getBirthDate());


            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();

            object.setId(generatedKeys.getObject(DIRECTOR_ID, Long.class));

            return object;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Director> update(Director object) {


        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL.getQuery())) {

            preparedStatement.setObject(1, object.getName());
            preparedStatement.setObject(2, object.getBirthDate());
            preparedStatement.setObject(3, object.getId());

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

            preparedStatement.setObject(1, id);

            int update = preparedStatement.executeUpdate();

            return update > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Director> findById(Long id) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID.getQuery())) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            Director director = null;

            while (resultSet.next()) {
                if (director == null) {
                    director = buildDirector(resultSet);
                }

                Film film = buildFilm(resultSet);

                director.addFilm(film);

            }

            return Optional.ofNullable(director);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Optional<Director> findByName(String name) {


        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_DIRECTOR.getQuery())) {

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            Director director = null;

            while (resultSet.next()) {
                if (director == null) {
                    director = buildDirector(resultSet);
                }

                Film film = buildFilm(resultSet);

                director.addFilm(film);

            }

            return Optional.ofNullable(director);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Director> findAll() {
        Map<Long, Director> directorMap = new HashMap<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL.getQuery())) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Director director;
                Film film;

                long idDirector = resultSet.getLong(DIRECTOR_ID);

                if (directorMap.containsKey(idDirector)) {
                    director = directorMap.get(idDirector);
                } else {
                    director = buildDirector(resultSet);
                    directorMap.put(director.getId(), director);
                }

                film = buildFilm(resultSet);

                film.setDirector(director);
                director.addFilm(film);

            }

            return Set.copyOf(directorMap.values());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    private Director buildDirector(ResultSet resultSetFilms) throws SQLException {
        return new Director(
                resultSetFilms.getLong(DIRECTOR_ID),
                resultSetFilms.getString("name_director"),
                LocalDate.parse(resultSetFilms.getString("birth_date_director"), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                new ArrayList<>()
        );
    }

    private Film buildFilm(ResultSet resultSetFilms) throws SQLException {
        try {
            return new Film(
                    resultSetFilms.getLong("id_film"),
                    resultSetFilms.getString("name_film"),
                    Country.valueOf(resultSetFilms.getString("country")),
                    LocalDate.parse(resultSetFilms.getString("date_realize"), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    Genre.valueOf(resultSetFilms.getString("genre")),
                    null
            );
        } catch (NullPointerException e) {
            return new Film();
        }
    }
}
