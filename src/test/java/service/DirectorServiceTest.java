package service;

import dao.DirectorDao;
import dto.DirectorDto;
import exception.ValidationException;
import mapper.DirectorMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import validator.DirectorValidator;
import validator.ErrorValidation;
import validator.ValidationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static util.ObjectsForTest.*;

@ExtendWith(MockitoExtension.class)
class DirectorServiceTest {

    @Mock
    private DirectorDao directorDao;
    @Mock
    private DirectorValidator directorValidator;
    @Mock
    private DirectorMapper directorMapper;
    @InjectMocks
    private DirectorService directorService;


    @Test
    void findAllTest() {

        Mockito.doReturn(Set.of(DIRECTOR_1)).when(directorDao).findAll();
        Mockito.doReturn(DIRECTOR_DTO_1).when(directorMapper).directorToDirectorDto(DIRECTOR_1);

        assertThat(List.of(DIRECTOR_DTO_1)).isEqualTo(directorService.findAll());

    }

    @Test
    void findByIDTest() {

        Mockito.doReturn(Optional.of(DIRECTOR_1)).when(directorDao).findById(DIRECTOR_1.getId());
        Mockito.doReturn(DIRECTOR_DTO_1).when(directorMapper).directorToDirectorDto(DIRECTOR_1);

        assertThat(Optional.of(DIRECTOR_DTO_1)).isEqualTo(directorService.findById(DIRECTOR_1.getId()));

    }

    @Test
    void createDirectorTestOK() {

        Mockito.doReturn(new ValidationResult()).when(directorValidator).validate(DIRECTOR_DTO_1);
        Mockito.doReturn(DIRECTOR_1).when(directorMapper).directorDtoToDirector(DIRECTOR_DTO_1);
        Mockito.doReturn(DIRECTOR_1).when(directorDao).create(DIRECTOR_1);
        Mockito.doReturn(DIRECTOR_DTO_1).when(directorMapper).directorToDirectorDto(DIRECTOR_1);

        assertThat(directorService.create(DIRECTOR_DTO_1)).isEqualTo(DIRECTOR_DTO_1);
    }

    @Test
    void createWithValidationError() {
        ValidationResult result = new ValidationResult();
        result.add(new ErrorValidation("error.nameDirector", "Name director is invalid"));

        Mockito.doReturn(result).when(directorValidator).validate(new DirectorDto());

        assertThrows(ValidationException.class,
                () -> directorService.create(new DirectorDto()));
    }


    @Test
    void updateDirectorOK() {

        Mockito.doReturn(DIRECTOR_DTO_1).when(directorMapper).directorToDirectorDto(DIRECTOR_1);
        Mockito.doReturn(new ValidationResult()).when(directorValidator).validate(DIRECTOR_DTO_1);
        Mockito.doReturn(DIRECTOR_1).when(directorMapper).directorDtoToDirector(DIRECTOR_DTO_1);
        Mockito.doReturn(Optional.of(DIRECTOR_1)).when(directorDao).update(DIRECTOR_1);

        assertThat(directorService.update(DIRECTOR_DTO_1)).isEqualTo(DIRECTOR_DTO_1);

    }

    @Test
    void updateWithWrongID() {


        assertThrows(RuntimeException.class,
                () -> directorService.update(DIRECTOR_DTO_1));

    }

    @Test
    void updateWithValidateErrors() {

        ValidationResult result = new ValidationResult();
        result.add(new ErrorValidation("error.DateRealize", "Date realize is invalid. Use pattern yyyy-MM-dd"));


        Mockito.doReturn(result).when(directorValidator).validate(DIRECTOR_DTO_1);

        assertThrows(ValidationException.class,
                () -> directorService.update(DIRECTOR_DTO_1));
    }

    @Test
    void updateIfSomethingWrongWithDB() {



        Mockito.doReturn(new ValidationResult()).when(directorValidator).validate(DIRECTOR_DTO_1);
        Mockito.doReturn(DIRECTOR_1).when(directorMapper).directorDtoToDirector(DIRECTOR_DTO_1);
        Mockito.doReturn(Optional.empty()).when(directorDao).update(DIRECTOR_1);

        assertThat(new DirectorDto()).isEqualTo(directorService.update(DIRECTOR_DTO_1));

    }

    @Test
    void updateWhenIDOkAndEmptyBody() {
        DirectorDto updateDirector = new DirectorDto(DIRECTOR_DTO_1.getId(), null, null, new ArrayList<>());
        ValidationResult validationResult = new ValidationResult();
        validationResult.add(new ErrorValidation("error.nameDirector", "Name director is invalid"));

        Mockito.doReturn(validationResult)
                .when(directorValidator).validate(updateDirector);


        assertThrows(ValidationException.class, () -> directorService.update(updateDirector));
    }

    @Test
    void deleteIsOk() {

        Mockito.doReturn(true).when(directorDao).delete(1L);

        assertTrue(directorService.delete(1L));

    }

    @Test
    void deleteIsNotOk() {

        Mockito.doReturn(false).when(directorDao).delete(10L);

        assertFalse(directorService.delete(10L));

    }
}