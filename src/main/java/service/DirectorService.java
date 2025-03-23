package service;


import dao.DirectorDao;
import dto.DirectorDto;
import entity.Director;
import exception.ValidationException;
import mapper.DirectorMapper;
import validator.DirectorValidator;
import validator.ValidationResult;

import java.util.List;
import java.util.Optional;

public class DirectorService {


    private DirectorDao directorDao;
    private DirectorValidator directorValidator;
    private DirectorMapper directorMapper;

    public DirectorService() {
        this.directorDao = new DirectorDao();
        this.directorValidator = DirectorValidator.getInstance();
        this.directorMapper = new DirectorMapper();
    }

    public DirectorService(DirectorDao directorDao, DirectorValidator directorValidator, DirectorMapper directorMapper) {
        this.directorDao = directorDao;
        this.directorValidator = directorValidator;
        this.directorMapper = directorMapper;
    }

    public List<DirectorDto> findAll() {
        return directorDao.findAll()
                .stream()
                .map(directorMapper::directorToDirectorDto)
                .toList();
    }

    public Optional<DirectorDto> findById(Long id) {
        return directorDao.findById(id)
                .map(directorMapper::directorToDirectorDto);
    }

    public DirectorDto create(DirectorDto directorDto) {

        ValidationResult validate = directorValidator.validate(directorDto);

        if (!validate.isValid()) {
            throw new ValidationException(validate.getErrors());
        }

        Director newDirector = directorMapper.directorDtoToDirector(directorDto);

        newDirector = directorDao.create(newDirector);

        return directorMapper.directorToDirectorDto(newDirector);
    }

    public boolean delete(Long id) {
        return directorDao.delete(id);
    }

    public DirectorDto update(DirectorDto updateDirector) {

        ValidationResult validate = directorValidator.validate(updateDirector);

        if (!validate.isValid()) {
            throw new ValidationException(validate.getErrors());
        }

        Director update = directorMapper.directorDtoToDirector(updateDirector);

        Optional<Director> optionalDirector = directorDao.update(update);

        if (optionalDirector.isPresent()) {
            return optionalDirector.map(directorMapper::directorToDirectorDto).get();
        } else {
            return new DirectorDto();
        }

    }
    
}
