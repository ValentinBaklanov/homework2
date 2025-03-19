package validator;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private final List<ErrorValidation> errors = new ArrayList<>();

    public void add(ErrorValidation error) {
        errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<ErrorValidation> getErrors() {
        return errors;
    }
}
