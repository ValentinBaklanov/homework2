package validator;

import dto.FilmDto;
import entity.Country;
import entity.Genre;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FilmValidator {


    public FilmValidator() {// Noncompliant - method is empty
    }

    public ValidationResult validate(FilmDto filmDto) {

        ValidationResult result = new ValidationResult();

        if (filmDto.getNameFilm() == null || filmDto.getNameFilm().isEmpty()) {
            result.add(new ErrorValidation("error.nameFilm", "Name Film is invalid"));
        }
        if (!countryIsValid(filmDto.getCountry())) {
            result.add(new ErrorValidation("error.Country", "Country is invalid"));
        }
        if (!dateIsValid(filmDto.getDateRealize())) {
            result.add(new ErrorValidation("error.DateRealize", "Date realize is invalid. Use pattern yyyy-MM-dd"));
        }
        if (!genreIsValid(filmDto.getGenre())) {
            result.add(new ErrorValidation("error.Genre", "Genre is invalid"));
        }

        return result;
    }

    private boolean dateIsValid(String dateRealize) {
        try {
            LocalDate.parse(dateRealize, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeException | NullPointerException e) {
            return false;
        }
    }

    private boolean genreIsValid(String genre) {
        try {
            Genre.valueOf(genre);
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
    }

    private boolean countryIsValid(String name) {
        try {
            Country.valueOf(name);
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
    }
}
