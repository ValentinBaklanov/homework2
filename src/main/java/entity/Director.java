package entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Director {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private List<Film> films;

    public Director(Long id, String name, LocalDate birthDate, List<Film> films) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.films = films;
    }

    public void addFilm(Film film) {
        this.films.add(film);
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return Objects.equals(id, director.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Director{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", birthDate=" + birthDate +
               '}';
    }
}
