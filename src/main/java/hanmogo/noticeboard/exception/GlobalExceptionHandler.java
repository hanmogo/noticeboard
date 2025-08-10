package hanmogo.noticeboard.exception;

import jakarta.validation.Valid;
import org.hibernate.Internal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// 애플리케이션 전역 예외 처리 핸들러 REST API 환경에서 발생하는 다양한 예외들을 한 곳에서 처리
// 통일된 응답 형식을 제공
@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid유효성 검사 실패 시 발생하는 예외를 처리하는 핸들러
    //HTTP 상태 코드 400 Bad Request와 함께 어떤 필드에서 유효성 검사가 실패했는지 응답

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    //IllegalArgumentException 처리 핸들러
    //주로 서비스 계층에서 비즈니스 규칙 위반 시 발생하는 예외를 처리
    //HTTP 상태 코드 400 Bad Request와 함께 예외 메시지를 응답
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


     //그 외 모든 예외를 처리하는 핸들러
     //예측하지 못한 서버 내부 오류를 처리하여 HTTP 상태 코드 500 Internal Server Error를 응답합니다.

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>("Internal Server Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
