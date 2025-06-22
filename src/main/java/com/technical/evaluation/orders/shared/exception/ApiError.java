package com.technical.evaluation.orders.shared.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.technical.evaluation.orders.shared.dto.ApiResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulate exceptions thrown by service layer.
 */
@Getter
@Setter
@NoArgsConstructor
public class ApiError {

    @Schema(description = "Code d'erreur personnalisé")
    @Setter(AccessLevel.NONE)
    @JsonProperty("error_code")
    private String errorCode;

    private String message;

    private List<?> details = new ArrayList<>();

    public ApiError(ApiResponseCode errorCode, String message) {
        this.errorCode = errorCode.getLabel();
        this.message = message;
    }

    public ApiError(ApiResponseCode errorCode, String message, List<?> details) {
        this(errorCode, message);
        this.details = details;
    }

    public ApiError(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
