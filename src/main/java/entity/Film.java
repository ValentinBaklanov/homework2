package entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;


public class Film {

    private Long id;
    @NotNull
    @NotBlank
    private String nameFilm;
    @NotNull
    private Country country;
    @NotNull
    private LocalDate dateRealize;
    @NotNull
    private Genre genre;
    @NotNull
    private Director director;

    public Film(Long id, String nameFilm, Country country, LocalDate dateRealize, Genre genre, Director director) {
        this.id = id;
        this.nameFilm = nameFilm;
        this.country = country;
        this.dateRealize = dateRealize;
        this.genre = genre;
        this.director = director;
    }

    public Film() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameFilm() {
        return nameFilm;
    }

    public void setNameFilm(String nameFilm) {
        this.nameFilm = nameFilm;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public LocalDate getDateRealize() {
        return dateRealize;
    }

    public void setDateRealize(LocalDate dateRealize) {
        this.dateRealize = dateRealize;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(id, film.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Film{" +
               "id=" + id +
               ", nameFilm='" + nameFilm + '\'' +
               ", country=" + country +
               ", dateRealize=" + dateRealize +
               ", genre=" + genre +
               ", director=" + director +
               '}';
    }
}
