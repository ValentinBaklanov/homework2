package mapper;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import static util.ObjectsForTest.*;


class FilmMapperTest {

    private static final FilmMapper filmMapper = new FilmMapper();

    @Test
    void entityToDto() {


        assertThat(filmMapper.filmToFilmDto(FILM_1)).isEqualTo(FILM_DTO_1);

    }

    @Test
    void dtoToEntity() {

        assertThat(filmMapper.filmDtoToFilm(FILM_DTO_1)).isEqualTo(FILM_1);


    }

}