package util;

import dto.DirectorDto;
import dto.FilmDto;
import entity.Country;
import entity.Director;
import entity.Film;
import entity.Genre;

import java.time.LocalDate;
import java.util.ArrayList;

public final class ObjectsForTest {


    public static final DirectorDto DIRECTOR_DTO_1 = new DirectorDto(
            4L,
            "Petr Petrov",
            LocalDate.of(2000, 1, 1).toString(),
            new ArrayList<>()
    );
    public static final FilmDto FILM_DTO_1 = new FilmDto(
            2L,
            "Ivan da Maria",
            "CANADA",
            LocalDate.now().toString(),
            "DRAMA",
            DIRECTOR_DTO_1.getId()
    );
    public static final Director DIRECTOR_1 = new Director(
            4L,
            "Petr Petrov",
            LocalDate.of(2000, 1, 1),
            new ArrayList<>()
    );

    public static final Director DIRECTOR_2 = new Director(
            3L,
            "Ivan Ivanov",
            LocalDate.of(2010, 1, 1),
            new ArrayList<>()
    );
    public static final Film FILM_2 = new Film(
            1L,
            "One Two Three",
            Country.JAPAN,
            LocalDate.now(),
            Genre.ACTION,
            DIRECTOR_2
    );
    public static final DirectorDto DIRECTOR_DTO_2 = new DirectorDto(
            3L,
            "Ivan Ivanov",
            LocalDate.of(2010, 1, 1).toString(),
            new ArrayList<>()
    );
    public static final FilmDto FILM_DTO_2 = new FilmDto(
            1L,
            "One Two Three",
            "JAPAN",
            LocalDate.now().toString(),
            "ACTION",
            DIRECTOR_2.getId()
    );

    public static final Film FILM_1 = new Film(
            2L,
            "Ivan da Maria",
            Country.CANADA,
            LocalDate.now(),
            Genre.DRAMA,
            DIRECTOR_1
    );


}
