package com.technical.evaluation.orders.shared.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technical.evaluation.orders.shared.dto.ApiResponseCode;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

import static com.technical.evaluation.orders.shared.dto.ApiResponseCode.BAD_REQUEST;
import static com.technical.evaluation.orders.shared.dto.ApiResponseCode.DATA_NOT_FOUND;


/**
 * Handle all exception for the API.
 */
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final MessageSource customMessageSource;



    /**
     * Uniformize all error into custom error format.
     *
     * @param body       the body
     * @param headers    headers
     * @param statusCode http status code
     * @param request    request
     * @return ResponseEntity of {@link ApiError}
     */
    @Override
    @NonNull
    protected ResponseEntity<Object> createResponseEntity(Object body, @NonNull HttpHeaders headers, @NonNull HttpStatusCode statusCode, @NonNull WebRequest request) {
        log.error("Error occured {}", body);
        String message = body != null ? body.toString() : "Invalid request";
        if (body instanceof ProblemDetail problemDetail) {
            message = problemDetail.getDetail();
        }
        return new ResponseEntity<>(new ApiError(HttpStatus.valueOf(statusCode.value())
                .getReasonPhrase().toLowerCase().replace(" ", "-"), message), headers, statusCode);
    }

    /**
     * This method handle request parameter and path variable validation error.
     *
     * @param e the ApplicationException.
     * @return ResponseEntity<ApiError>
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException e) {
        log.info(e.getLocalizedMessage());
        return new ResponseEntity<>(new ApiError(BAD_REQUEST, "Invalid request parameters."), HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handle application exception error.
     *
     * @param e the ApplicationException.
     * @return ResponseEntity<ApiError>.
     */
    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleApplicationException(ApplicationException e) {
        return new ResponseEntity<>(new ApiError(e.getCode(), e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handle date time parse error.
     *
     * @param e the DateTimeParseException.
     * @return ResponseEntity<ApiError>.
     */
    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleDateTimeParseException(DateTimeParseException e) {
        return new ResponseEntity<>(new ApiError(BAD_REQUEST, "Date '%s' is invalid".formatted(e.getParsedString())), HttpStatus.BAD_REQUEST);
    }


    /**
     * Encodage par défaut des messages json
     */
    public static final Charset ENCODING = StandardCharsets.UTF_8;

    /**
     * Send response as JSON.
     *
     * @param error    Api error to send.
     * @param response The http response.
     */
    public static void setJsonResponse(ApiError error, HttpServletResponse response, HttpStatus httpStatus) {
        response.setStatus(httpStatus.value());
        try {
            String json = mapper.writeValueAsString(error);
            response.setContentType("application/json");
            response.setCharacterEncoding(ENCODING.name());
            OutputStream os = response.getOutputStream();
            os.write(json.getBytes(ENCODING));
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public ResponseEntity<Object> createApiErrorResponseEntity(
            WebRequest webRequest, ApiResponseCode apiResponseCode, String messageKey, List<?> details, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ApiError(apiResponseCode, customMessageSource.getMessage(
                messageKey, null, webRequest.getLocale()), details == null ? Collections.emptyList() : details), httpStatus);
    }

    /**
     * This method handle data not found exception.
     *
     * @param e the DataNotFoundException.
     * @return ResponseEntity<ApiError>.
     */
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleDataNotFoundException(DataNotFoundException e) {
        return new ResponseEntity<>(new ApiError(DATA_NOT_FOUND, e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }
}
