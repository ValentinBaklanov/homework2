package dto;


import java.util.Objects;

public class DirectorDtoForFilm {

    private Long id;
    private String name;
    private String birthDate;

    public DirectorDtoForFilm(Long id, String name, String birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public DirectorDtoForFilm() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectorDtoForFilm that = (DirectorDtoForFilm) o;
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
