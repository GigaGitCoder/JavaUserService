package library.user.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Обработка некорректных аргументов (400 Bad Request)
    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Invalid request", ex.getMessage(),
                HttpStatus.BAD_REQUEST, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Обработка случая, когда пользователь не найден (404 Not Found)
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException ex) {
        logger.error("AccountNotFoundException: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("User not found", ex.getMessage(),
                HttpStatus.NOT_FOUND, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // Обработка случая, когда email уже используется (409 Conflict)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        logger.error("EmailAlreadyExistsException: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Email already exists", ex.getMessage(),
                HttpStatus.CONFLICT, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    // Обработка ошибки шифрования пароля (500 Internal Server Error)
    @ExceptionHandler(PasswordEncryptionException.class)
    public ResponseEntity<ErrorResponse> handlePasswordEncryptionException(PasswordEncryptionException ex) {
        logger.error("PasswordEncryptionException: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Password encryption error", ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // Обработка всех остальных ошибок времени выполнения (500 Internal Server Error)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        logger.error("RuntimeException: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Internal server error", ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // Общий обработчик для всех непредусмотренных исключений (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.error("Exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Unexpected error", ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
