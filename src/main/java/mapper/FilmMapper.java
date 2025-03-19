package mapper;

import dto.FilmDto;
import entity.Country;
import entity.Director;
import entity.Film;
import entity.Genre;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class FilmMapper {


    public FilmMapper() {
        // this constructor is empty
    }

    public FilmDto filmToFilmDto(Film film) {

        FilmDto filmDto = buildFilmDto(film);
        filmDto.setDirectorDto(film.getDirector().getId());

        return filmDto;
    }

    public Film filmDtoToFilm(FilmDto filmDto) {

        return new Film(
                filmDto.getId(),
                filmDto.getNameFilm(),
                Country.valueOf(filmDto.getCountry()),
                LocalDate.parse(filmDto.getDateRealize(), DateTimeFormatter.ISO_LOCAL_DATE),
                Genre.valueOf(filmDto.getGenre()),
                new Director(
                        filmDto.getDirectorDto(),
                        null,
                        null,
                        null
                )
        );
    }


    public FilmDto buildFilmDto(Film film) {
        try {
            FilmDto filmDto;
            filmDto = new FilmDto(
                    film.getId(),
                    film.getNameFilm(),
                    film.getCountry().name(),
                    film.getDateRealize().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    film.getGenre().name(),
                    null
            );
            return filmDto;
        } catch (NullPointerException e) {
            return new FilmDto();
        }
    }
}
