package validator;

import dto.DirectorDto;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DirectorValidator {

    private static final DirectorValidator INSTANCE = new DirectorValidator();

    private DirectorValidator() {
    }

    public static DirectorValidator getInstance() {
        return INSTANCE;
    }

    public ValidationResult validate(DirectorDto directorDto) {

        ValidationResult result = new ValidationResult();

        if (directorDto.getName() == null || directorDto.getName().isEmpty()) {
            result.add(new ErrorValidation("error.nameDirector", "Name director is invalid"));
        }

        if (!dateIsValid(directorDto.getBirthDate())) {
            result.add(new ErrorValidation("error.DateRealize", "Date realize is invalid. Use pattern yyyy-MM-dd"));
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

}
