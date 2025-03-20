package mapper;

import dto.DirectorDto;
import entity.Director;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ObjectsForTest.*;

@ExtendWith(MockitoExtension.class)
class DirectorMapperTest {

    private DirectorMapper directorMapper = new DirectorMapper();

    @Test
    void entityToDto() {

        Director director1 = DIRECTOR_1;
        director1.addFilm(FILM_1);
        DirectorDto directorDto = directorMapper.directorToDirectorDto(director1);

        assertThat(directorDto).isEqualTo(DIRECTOR_DTO_1);

    }

    @Test
    void dtoToEntity() {

        DirectorDto directorDto1 = DIRECTOR_DTO_1;
        directorDto1.getFilmsDto().add(FILM_DTO_1);
        Director director = directorMapper.directorDtoToDirector(directorDto1);

        assertThat(director).isEqualTo(DIRECTOR_1);


    }

}