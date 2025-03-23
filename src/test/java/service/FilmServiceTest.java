package service;

import dao.FilmDao;
import dto.FilmDto;
import exception.ValidationException;
import mapper.FilmMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import validator.ErrorValidation;
import validator.FilmValidator;
import validator.ValidationResult;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static util.ObjectsForTest.*;


@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmDao filmDao;

    @Mock
    private FilmValidator filmValidator;

    @Mock
    private FilmMapper filmMapper;

    @InjectMocks
    private FilmService filmService;


    @Test
    void findAllTest() {

        Mockito.doReturn(Set.of(FILM_1)).when(filmDao).findAll();
        Mockito.doReturn(FILM_DTO_1).when(filmMapper).filmToFilmDto(FILM_1);

        List<FilmDto> filmDtos = filmService.findAll();

        assertThat(List.of(FILM_DTO_1)).isEqualTo(filmDtos);
        assertThat(filmDtos).hasSize(1);

    }

    @Test
    void findByIDTest() {

        Mockito.doReturn(Optional.of(FILM_1)).when(filmDao).findById(2L);
        Mockito.doReturn(FILM_DTO_1).when(filmMapper).filmToFilmDto(FILM_1);

        assertThat(Optional.of(FILM_DTO_1)).isEqualTo(filmService.findById(2L));

    }

    @Test
    void createFilmTestOK() {

        Mockito.doReturn(new ValidationResult()).when(filmValidator).validate(FILM_DTO_1);
        Mockito.doReturn(FILM_1).when(filmMapper).filmDtoToFilm(FILM_DTO_1);
        Mockito.doReturn(FILM_1).when(filmDao).create(FILM_1);
        Mockito.doReturn(FILM_DTO_1).when(filmMapper).filmToFilmDto(FILM_1);

        assertThat(filmService.create(FILM_DTO_1)).isEqualTo(FILM_DTO_1);
    }

    @Test
    void createFilmWithErrors() {

        ValidationResult result = new ValidationResult();
        result.add(new ErrorValidation("error.nameFilm", "Name Film is invalid"));

        Mockito.doReturn(result).when(filmValidator).validate(new FilmDto());

        assertThrows(ValidationException.class,
                () -> filmService.create(new FilmDto()));
    }

    @Test
    void updateFilmOK() {


        Mockito.doReturn(FILM_DTO_1).when(filmMapper).filmToFilmDto(FILM_1);
        Mockito.doReturn(new ValidationResult()).when(filmValidator).validate(FILM_DTO_1);
        Mockito.doReturn(FILM_1).when(filmMapper).filmDtoToFilm(FILM_DTO_1);
        Mockito.doReturn(Optional.of(FILM_1)).when(filmDao).update(FILM_1);


        assertThat(filmService.update(FILM_DTO_1)).isEqualTo(FILM_DTO_1);

    }

    @Test
    void updateWithWrongID() {


        assertThrows(RuntimeException.class,
                () -> filmService.update(FILM_DTO_1));

    }

    @Test
    void updateWithValidateErrors() {

        ValidationResult result = new ValidationResult();
        result.add(new ErrorValidation("error.Country", "Country is invalid"));

        Mockito.doReturn(result).when(filmValidator).validate(FILM_DTO_1);

        assertThrows(ValidationException.class,
                () -> filmService.update(FILM_DTO_1));
    }

    @Test
    void updateIfSomethingWrongWithDB() {


        Mockito.doReturn(new ValidationResult()).when(filmValidator).validate(FILM_DTO_1);
        Mockito.doReturn(FILM_1).when(filmMapper).filmDtoToFilm(FILM_DTO_1);
        Mockito.doReturn(Optional.empty()).when(filmDao).update(FILM_1);

        assertThat(new FilmDto()).isEqualTo(filmService.update(FILM_DTO_1));

    }

    @Test
    void updateWhenIDOkAndEmptyBody() {
        FilmDto updateFilmDto = new FilmDto(2L, null, null, null, null, null);

        Mockito.doReturn(FILM_DTO_1).when(filmMapper).filmToFilmDto(FILM_1);
        Mockito.doReturn(new ValidationResult()).when(filmValidator).validate(FILM_DTO_1);
        Mockito.doReturn(FILM_1).when(filmMapper).filmDtoToFilm(FILM_DTO_1);
        Mockito.doReturn(Optional.of(FILM_1)).when(filmDao).update(FILM_1);


        assertThat(filmService.update(updateFilmDto)).isEqualTo(FILM_DTO_1);
    }

    @Test
    void deleteIsOk() {

        Mockito.doReturn(true).when(filmDao).delete(1L);

        assertTrue(filmService.delete(1L));

    }

    @Test
    void deleteIsNotOk() {

        Mockito.doReturn(false).when(filmDao).delete(10L);

        assertFalse(filmService.delete(10L));

    }

}