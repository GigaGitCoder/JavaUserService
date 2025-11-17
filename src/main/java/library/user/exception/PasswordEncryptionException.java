package library.user.exception;

public class PasswordEncryptionException extends RuntimeException {
    public PasswordEncryptionException(String message) {
        super(message);
    }
}
