package dao;

import connection.DataSource;
import entity.Director;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import util.PrepareTableForTest;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DirectorDaoTest {

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    private static DataSource dataSource;

    private DirectorDao directorDao;

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
        directorDao = new DirectorDao(dataSource);
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

        Director director = directorDao.create(new Director(null, "Petr", LocalDate.now(), new ArrayList<>()));
        Director director2 = directorDao.create(new Director(null, "Ivan", LocalDate.now(), new ArrayList<>()));
        Director director3 = directorDao.create(new Director(null, "Victor", LocalDate.now(), new ArrayList<>()));
        Director director4 = directorDao.create(new Director(null, "Vadim", LocalDate.now(), new ArrayList<>()));

        assertThat(director).isEqualTo(directorDao.findById(director.getId()).get());
        assertThat(director2).isEqualTo(directorDao.findById(director2.getId()).get());
        assertThat(director3).isEqualTo(directorDao.findById(director3.getId()).get());
        assertThat(director4).isEqualTo(directorDao.findById(director4.getId()).get());

        Set<Director> all = directorDao.findAll();

        assertThat(all).hasSize(7);
        assertTrue(all.contains(director));
        assertTrue(all.contains(director2));
        assertTrue(all.contains(director3));
        assertTrue(all.contains(director4));

    }

    @Test
    void findAllCheck() {

        Set<Director> all = directorDao.findAll();

        assertThat(all).hasSize(3);

    }

    @Test
    void findByNameCheckFilm() {

        Optional<Director> director = directorDao.findByName("Quentin Tarantino");

        assertThat(director.get().getFilms()).hasSize(2);
    }

    @Test
    void updateDirectorOK() {

        Director director = directorDao.findById(2L).get();
        director.setName("Petr");
        Optional<Director> updateDirector = directorDao.update(director);

        assertTrue(updateDirector.isPresent());
        assertThat(director.getName()).isEqualTo(updateDirector.get().getName());

    }

    @Test
    void updateDirectorWithInvalidId() {


        Director directorWrongId = new Director(55L, "null", LocalDate.now(), new ArrayList<>());
        assertThat(Optional.empty()).isEqualTo(directorDao.update(directorWrongId));

    }

    @Test
    void createIfExist() {

        Optional<Director> directorByName = directorDao.findByName("Quentin Tarantino");

        Director director = directorByName.get();

        assertThrows(RuntimeException.class,
                () -> directorDao.create(director));

    }

    @Test
    void deleteOk() {

        Optional<Director> directorByName = directorDao.findByName("Peter Jackson");

        assertTrue(directorDao.delete(directorByName.get().getId()));

        Set<Director> all = directorDao.findAll();

        assertThat(all).hasSize(2);

    }

    @Test
    void deleteIsNotOk() {

        assertFalse(directorDao.delete(99L));

    }

}