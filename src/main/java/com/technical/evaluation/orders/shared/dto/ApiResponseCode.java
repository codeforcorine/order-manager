package com.technical.evaluation.orders.shared.dto;

import lombok.Getter;

@Getter
public enum ApiResponseCode {
    INTERNAL_SERVER_ERROR("internal-server-error"),
    STATUS_NOT_FOUND("status-not-found"),
    DATA_NOT_FOUND("data-not-found"),
    DATA_ALREADY_EXISTS("data-already-exist"),
    BAD_REQUEST("bad-request"),
    REQUEST_VALIDATION_ERROR("request-validation-error"),
    SUCCESS("success"),
    FAIL("fail");
    private final String label;

    ApiResponseCode(String label) {
        this.label = label;
    }
}
