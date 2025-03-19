package dao;

import connection.DataSource;
import entity.Country;
import entity.Director;
import entity.Film;
import entity.Genre;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import util.PrepareTableForTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FilmDaoTest {

    private static final  PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    private static DataSource dataSource;

    private FilmDao filmDao;

    @BeforeAll
    static void beforeAll() {

        postgres.start();

        dataSource = new DataSource(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

    }

    @AfterAll
    static void afterAll() {

        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        filmDao = new FilmDao(dataSource);
        PrepareTableForTest.createDirectorTable(dataSource);
        PrepareTableForTest.createFilmTable(dataSource);
        PrepareTableForTest.insertDirectors(dataSource);
        PrepareTableForTest.insertFilms(dataSource);
    }

    @AfterEach
    void cleanTable() {
        PrepareTableForTest.dropTables(dataSource);
    }

    @Test
    void createAndFindAll() {

        Film film = filmDao.create(new Film(null, "Petr", Country.CANADA, LocalDate.now(), Genre.DRAMA, new Director(1L, null, null, null)));
        Film film2 = filmDao.create(new Film(null, "NONo", Country.USA, LocalDate.now(), Genre.ACTION, new Director(1L, null, null, null)));
        Film film3 = filmDao.create(new Film(null, "Privat", Country.UK, LocalDate.now(), Genre.FANTASY, new Director(2L, null, null, null)));
        Film film4 = filmDao.create(new Film(null, "P11", Country.JAPAN, LocalDate.now(), Genre.DRAMA, new Director(3L, null, null, null)));


        assertThat(film).isEqualTo(filmDao.findById(film.getId()).get());
        assertThat(film2).isEqualTo(filmDao.findById(film2.getId()).get());
        assertThat(film3).isEqualTo(filmDao.findById(film3.getId()).get());
        assertThat(film4).isEqualTo(filmDao.findById(film4.getId()).get());

        Set<Film> all = filmDao.findAll();

        assertThat(all).hasSize(8);
        assertTrue(all.contains(film));
        assertTrue(all.contains(film2));
        assertTrue(all.contains(film3));
        assertTrue(all.contains(film4));

    }

    @Test
    void findAllCheck() {

        Set<Film> all = filmDao.findAll();

        assertThat(all).hasSize(4);

    }


    @Test
    void updateFilmOK() {


        Film film = filmDao.findById(2L).get();
        film.setNameFilm("Petr");
        Optional<Film> optionalFilm = filmDao.update(film);

        assertTrue(optionalFilm.isPresent());
        assertThat(film.getNameFilm()).isEqualTo(optionalFilm.get().getNameFilm());

    }

    @Test
    void updateDirectorWithInvalidId() {


        Film filmWrongId = new Film(55L, "null", Country.CANADA, LocalDate.now(), Genre.DRAMA, new Director(1L, null, null, null));
        assertThat(Optional.empty()).isEqualTo(filmDao.update(filmWrongId));

    }

    @Test
    void createIfExist() {

        Optional<Film> film = filmDao.findById(1L);

        Film film1 = film.get();

        assertThrows(RuntimeException.class,
                () -> filmDao.create(film1));

    }

    @Test
    void deleteOk() {


        assertTrue(filmDao.delete(1L));

        Set<Film> all = filmDao.findAll();

        assertThat(all).hasSize(3);

    }

    @Test
    void deleteIsNotOk() {

        assertFalse(filmDao.delete(99L));

    }


}