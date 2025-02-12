package com.kt.pet_server.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.kt.pet_server.global.base.BaseResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    // CustomExceptionHandler
    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Void> handleCustomException(CustomException e) {
        log.error("CustomException", e);
        return BaseResponse.onFailure(e.getMessage());
    }

    // MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Void> handleValidationException(MethodArgumentNotValidException e) {
        FieldError error = e.getBindingResult().getFieldErrors()
            .stream().findFirst().orElse(null);

        if (error != null) {
            log.error("ValidationException: {} - {}", error.getField(), error.getDefaultMessage());
            return BaseResponse.onFailure(error.getDefaultMessage());
        }
        return BaseResponse.onFailure("잘못된 요청입니다.");
    }

    // JSON parse error
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Void> handleJsonParseException(HttpMessageNotReadableException e) {
        log.error("JsonParseException", e);
        return BaseResponse.onFailure("JSON 형식이 잘못되었습니다.");
    }

    // Type mismatch error
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Void> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("TypeMismatchException", e);
        return BaseResponse.onFailure("타입 변환 오류입니다.");
    }

    // Missing request parameter
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Void> handleException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException", e);
        return BaseResponse.onFailure("필수 요청 파라미터가 누락되었습니다.");
    }

    // Unsupported HTTP method
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public BaseResponse<Void> handleException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException", e);
        return BaseResponse.onFailure("지원하지 않는 HTTP 메소드입니다.");
    }

    // Unsupported media type
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public BaseResponse<Void> handleException(HttpMediaTypeNotSupportedException e) {
        log.error("HttpMediaTypeNotSupportedException", e);
        return BaseResponse.onFailure("지원하지 않는 미디어 타입입니다.");
    }


    // Exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<Void> handleException(Exception e) {
        log.error("Exception", e);
        return BaseResponse.onFailure("서버 내부 오류입니다.");
    }
}
