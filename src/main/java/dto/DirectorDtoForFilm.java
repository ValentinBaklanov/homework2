package dto;


import java.util.List;
import java.util.Objects;

public class DirectorDto {

    private Long id;
    private String name;
    private String birthDate;
    private List<FilmDto> filmsDto;

    public DirectorDto(Long id, String name, String birthDate, List<FilmDto> filmsDto) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.filmsDto = filmsDto;
    }

    public DirectorDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public List<FilmDto> getFilmsDto() {
        return filmsDto;
    }

    public void setFilmsDto(List<FilmDto> filmsDto) {
        this.filmsDto = filmsDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectorDto that = (DirectorDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DirectorDto{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", birthDate='" + birthDate + '\'' +
               '}';
    }
}
