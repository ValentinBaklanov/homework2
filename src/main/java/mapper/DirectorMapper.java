package mapper;

import dto.DirectorDto;
import dto.DirectorDtoForFilm;
import dto.FilmDto;
import entity.Director;
import entity.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DirectorMapper {


    public DirectorDto directorToDirectorDto(Director director) {

        FilmDto filmDto;

        DirectorDto directorDto = buildDirectorDto(director);

        for (Film film : director.getFilms()) {

            filmDto = buildFilmDto(film);
            filmDto.setDirectorDto(buildDirectorWithoutFilms(director));
            directorDto.getFilmsDto().add(filmDto);

        }

        return directorDto;
    }


    public Director directorDtoToDirector(DirectorDto directorDto) {
        return buildDirector(directorDto);
    }

    private Director buildDirector(DirectorDto directorDto) {
        return new Director(
                directorDto.getId(),
                directorDto.getName(),
                LocalDate.parse(directorDto.getBirthDate(), DateTimeFormatter.ISO_LOCAL_DATE),
                new ArrayList<>()
        );
    }


    public DirectorDto buildDirectorDto(Director director) {
        return new DirectorDto(
                director.getId(),
                director.getName(),
                director.getBirthDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                new ArrayList<>()
        );
    }

    public DirectorDtoForFilm buildDirectorWithoutFilms(Director director) {
        return new DirectorDtoForFilm(
                director.getId(),
                director.getName(),
                director.getBirthDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
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
