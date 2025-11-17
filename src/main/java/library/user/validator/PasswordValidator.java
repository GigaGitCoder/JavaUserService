package library.user.validator;

public class PasswordValidator {

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,64}$";
    private static final String ERROR_MESSAGE = "Password must be 8-64 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character (@$!%*?&)";

    private PasswordValidator() {
    }

    public static boolean isValid(String password) {
        if (password == null) {
            return false;
        }
        return password.matches(PASSWORD_PATTERN);
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
