package dto;


import java.util.Objects;

public class FilmDto {

    private Long id;
    private String nameFilm;
    private String country;
    private String dateRealize;
    private String genre;
    private Long idDirectorDto;

    public FilmDto() {
    }

    public FilmDto(Long id, String nameFilm, String country, String dateRealize, String genre, Long idDirectorDto) {
        this.id = id;
        this.nameFilm = nameFilm;
        this.country = country;
        this.dateRealize = dateRealize;
        this.genre = genre;
        this.idDirectorDto = idDirectorDto;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDateRealize() {
        return dateRealize;
    }

    public void setDateRealize(String dateRealize) {
        this.dateRealize = dateRealize;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getDirectorDto() {
        return idDirectorDto;
    }

    public void setDirectorDto(Long idDirectorDto) {
        this.idDirectorDto = idDirectorDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmDto filmDto = (FilmDto) o;
        return Objects.equals(id, filmDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FilmDto{" +
               "id=" + id +
               ", nameFilm='" + nameFilm + '\'' +
               ", country='" + country + '\'' +
               ", dateRealize='" + dateRealize + '\'' +
               ", genre='" + genre + '\'' +
               ", idDirectorDto=" + idDirectorDto +
               '}';
    }
}
