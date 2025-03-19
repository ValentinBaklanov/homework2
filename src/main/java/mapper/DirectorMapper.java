package mapper;

import dto.DirectorDto;
import dto.FilmDto;
import entity.Director;
import entity.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DirectorMapper {


    private final FilmMapper filmMapper = new FilmMapper();

    public DirectorMapper() {
        // this constructor is empty
    }


    public DirectorDto directorToDirectorDto(Director director) {


        FilmDto filmDto = null;

        DirectorDto directorDto = buildDirectorDto(director);

        for (Film film : director.getFilms()) {

            filmDto = filmMapper.buildFilmDto(film);
            filmDto.setDirectorDto(directorDto.getId());
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

}
