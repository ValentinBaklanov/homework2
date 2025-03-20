package validator;

import dto.DirectorDtoForFilm;
import dto.FilmDto;
import entity.Country;
import entity.Genre;
import org.junit.jupiter.api.Test;
import util.ObjectsForTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FilmValidatorTest {

    private static final FilmValidator filmValidator = new FilmValidator();

    @Test
    void validateIsOk() {

        List<ErrorValidation> errors = filmValidator.validate(ObjectsForTest.FILM_DTO_1).getErrors();
        assertThat(new ValidationResult().getErrors()).isEqualTo(errors);

    }

    @Test
    void nameFilmError() {
        ValidationResult result = new ValidationResult();
        result.add(new ErrorValidation("error.nameFilm", "Name Film is invalid"));

        FilmDto filmDto = new FilmDto(
                1L,
                "",
                Country.JAPAN.toString(),
                LocalDate.now().toString(),
                Genre.DRAMA.name(),
                new DirectorDtoForFilm(
                        1L,
                        "Name",
                        "1999-11-11"
                )
        );

        ErrorValidation errorValidation = result.getErrors().getFirst();

        ErrorValidation expected = filmValidator.validate(filmDto).getErrors().getFirst();
        assertThat(errorValidation.getCode()).isEqualTo(expected.getCode());
        assertThat(errorValidation.getMessage()).isEqualTo(expected.getMessage());

        filmDto.setNameFilm(null);
        assertThat(errorValidation.getCode()).isEqualTo(expected.getCode());
        assertThat(errorValidation.getMessage()).isEqualTo(expected.getMessage());


    }

    @Test
    void countryError() {
        ValidationResult result = new ValidationResult();
        result.add(new ErrorValidation("error.Country", "Country is invalid"));

        FilmDto filmDto = new FilmDto(
                1L,
                "asdasd",
                null,
                LocalDate.now().toString(),
                Genre.DRAMA.name(),
                new DirectorDtoForFilm(
                        1L,
                        "Name",
                        "1999-11-11"
                )
        );

        ErrorValidation errorValidation = result.getErrors().getFirst();

        ErrorValidation expected = filmValidator.validate(filmDto).getErrors().getFirst();
        assertThat(errorValidation.getCode()).isEqualTo(expected.getCode());
        assertThat(errorValidation.getMessage()).isEqualTo(expected.getMessage());
    }

    @Test
    void dateError() {
        ValidationResult result = new ValidationResult();
        result.add(new ErrorValidation("error.DateRealize", "Date realize is invalid. Use pattern yyyy-MM-dd"));

        FilmDto filmDto = new FilmDto(
                1L,
                "asdasd",
                Country.JAPAN.toString(),
                "sdsf",
                Genre.DRAMA.name(),
                new DirectorDtoForFilm(
                        1L,
                        "Name",
                        "1999-11-11"
                )
        );

        ErrorValidation errorValidation = result.getErrors().getFirst();

        ErrorValidation expected = filmValidator.validate(filmDto).getErrors().getFirst();
        assertThat(errorValidation.getCode()).isEqualTo(expected.getCode());
        assertThat(errorValidation.getMessage()).isEqualTo(expected.getMessage());
    }

    @Test
    void genreError() {
        ValidationResult result = new ValidationResult();
        result.add(new ErrorValidation("error.Genre", "Genre is invalid"));

        FilmDto filmDto = new FilmDto(
                1L,
                "asdasd",
                Country.JAPAN.toString(),
                LocalDate.now().toString(),
                "Genre.DRAMA.name()",
                new DirectorDtoForFilm(
                        1L,
                        "Name",
                        "1999-11-11"
                )
        );

        ErrorValidation errorValidation = result.getErrors().getFirst();

        ErrorValidation expected = filmValidator.validate(filmDto).getErrors().getFirst();
        assertThat(errorValidation.getCode()).isEqualTo(expected.getCode());
        assertThat(errorValidation.getMessage()).isEqualTo(expected.getMessage());
    }

}