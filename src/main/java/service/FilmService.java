package service;

import dao.FilmDao;
import dto.FilmDto;
import entity.Film;
import exception.ValidationException;
import mapper.FilmMapper;
import validator.FilmValidator;
import validator.ValidationResult;

import java.util.List;
import java.util.Optional;

public class FilmService {


    private FilmDao filmDao;
    private FilmValidator filmValidator;
    private FilmMapper filmMapper;


    public FilmService() {
        this.filmDao = new FilmDao();
        this.filmValidator = new FilmValidator();
        this.filmMapper = new FilmMapper();
    }

    public FilmService(FilmDao filmDao, FilmValidator filmValidator, FilmMapper filmMapper) {
        this.filmDao = filmDao;
        this.filmValidator = filmValidator;
        this.filmMapper = filmMapper;
    }


    public List<FilmDto> findAll() {
        return filmDao.findAll().stream()
                .map(filmMapper::filmToFilmDto)
                .toList();
    }


    public Optional<FilmDto> findById(Long id) {
        return filmDao.findById(id)
                .map(filmMapper::filmToFilmDto);
    }


    public FilmDto update(FilmDto updateFilm) {

        Optional<FilmDto> filmOld = filmDao.findById(updateFilm.getId())
                .map(filmMapper::filmToFilmDto);

        if (filmOld.isPresent()) {
            updateFilm = setFilmChangingFieldsByUpdate(filmOld.get(), updateFilm);
        } else {
            throw new RuntimeException("Invalid ID film change the request");
        }

        ValidationResult result = filmValidator.validate(updateFilm);

        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }

        Film update = filmMapper.filmDtoToFilm(updateFilm);

        Optional<Film> optionalFilm = filmDao.update(update);

        if (optionalFilm.isPresent()) {
            return optionalFilm.map(filmMapper::filmToFilmDto).get();
        } else {
            return new FilmDto();
        }

    }

    public FilmDto create(FilmDto filmDto) {

        ValidationResult result = filmValidator.validate(filmDto);

        if (!result.isValid()) {
            throw new ValidationException(result.getErrors());
        }

        Film film = filmMapper.filmDtoToFilm(filmDto);

        film = filmDao.create(film);

        return filmMapper.filmToFilmDto(film);

    }

    public boolean delete(Long id) {
        return filmDao.delete(id);
    }

    private FilmDto setFilmChangingFieldsByUpdate(FilmDto filmOld, FilmDto updateFilm) {

        updateFilm.setNameFilm(updateFilm.getNameFilm() == null
                ? filmOld.getNameFilm()
                : updateFilm.getNameFilm());
        updateFilm.setCountry(updateFilm.getCountry() == null
                ? filmOld.getCountry()
                : updateFilm.getCountry());
        updateFilm.setDateRealize(updateFilm.getDateRealize() == null
                ? filmOld.getDateRealize()
                : updateFilm.getDateRealize());
        updateFilm.setGenre(updateFilm.getGenre() == null
                ? filmOld.getGenre()
                : updateFilm.getGenre());


        return updateFilm;

    }
}
