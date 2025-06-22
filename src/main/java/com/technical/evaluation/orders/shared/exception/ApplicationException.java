package com.technical.evaluation.orders.shared.exception;

import com.technical.evaluation.orders.shared.dto.ApiResponseCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode(callSuper = false)
public class ApplicationException extends RuntimeException {


    private ApiResponseCode code = ApiResponseCode.BAD_REQUEST;

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(ApiResponseCode code, String message) {
        super(message);
        this.code = code;
    }


    public ApplicationException(ApiResponseCode code, String message, Object... args) {
        super(message);
        this.code = code;
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
