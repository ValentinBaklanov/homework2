package exception;

import validator.ErrorValidation;

import java.util.List;

public class ValidationException extends RuntimeException {

    private final transient List<ErrorValidation> errors;


    public ValidationException(List<ErrorValidation> errors) {
        this.errors = errors;
    }

    public List<ErrorValidation> getErrors() {
        return errors;
    }
}
