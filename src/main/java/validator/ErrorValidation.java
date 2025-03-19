package validator;

public class ErrorValidation {

    private final String code;

    private final String message;

    public ErrorValidation(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
