package mapper;

import dto.DirectorDtoForFilm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import static util.ObjectsForTest.*;

class FilmMapperTest {

    private FilmMapper filmMapper = new FilmMapper();

    @Test
    void entityToDto() {


        assertThat(filmMapper.filmToFilmDto(FILM_1)).isEqualTo(FILM_DTO_1);

    }

    @Test
    void dtoToEntity() {


        assertThat(filmMapper.filmDtoToFilm(FILM_DTO_2)).isEqualTo(FILM_2);


    }

}