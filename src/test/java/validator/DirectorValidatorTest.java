package validator;

import dto.DirectorDto;
import org.junit.jupiter.api.Test;
import util.ObjectsForTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DirectorValidatorTest {

    private static final DirectorValidator directorValidator = DirectorValidator.getInstance();

    @Test
    void validateIsOK() {

        List<ErrorValidation> errors = new ValidationResult().getErrors();
        List<ErrorValidation> validationList = directorValidator.validate(ObjectsForTest.DIRECTOR_DTO_1).getErrors();

        assertThat(errors).isEqualTo(validationList);

    }

    @Test
    void dateIsNotValid() {

        ValidationResult result = new ValidationResult();
        result.add(new ErrorValidation("error.DateRealize", "Date realize is invalid. Use pattern yyyy-MM-dd"));
        ErrorValidation errorValidation = result.getErrors().getFirst();

        DirectorDto directorDto1 = new DirectorDto(
                1L,
                "ghgh",
                "2222",
                new ArrayList<>()
        );


        ErrorValidation expected = directorValidator.validate(directorDto1).getErrors().getFirst();
        assertThat(errorValidation.getCode()).isEqualTo(expected.getCode());
        assertThat(errorValidation.getMessage()).isEqualTo(expected.getMessage());

    }

    @Test
    void nameIsNotValid() {
        ValidationResult result = new ValidationResult();
        result.add(new ErrorValidation("error.nameDirector", "Name director is invalid"));
        ErrorValidation errorValidation = result.getErrors().getFirst();

        DirectorDto directorDto1 = new DirectorDto(
                1L,
                "",
                LocalDate.now().toString(),
                new ArrayList<>()
        );


        ErrorValidation expected = directorValidator.validate(directorDto1).getErrors().getFirst();
        assertThat(errorValidation.getCode()).isEqualTo(expected.getCode());
        assertThat(errorValidation.getMessage()).isEqualTo(expected.getMessage());


        directorDto1.setName(null);

        expected = directorValidator.validate(directorDto1).getErrors().getFirst();
        assertThat(errorValidation.getCode()).isEqualTo(expected.getCode());
        assertThat(errorValidation.getMessage()).isEqualTo(expected.getMessage());
    }

}