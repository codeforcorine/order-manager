package com.technical.evaluation.orders.shared.exception;

import com.technical.evaluation.orders.shared.dto.ApiResponseCode;

public class DataNotFoundException extends RuntimeException{
    private ApiResponseCode code = ApiResponseCode.DATA_NOT_FOUND;

    public DataNotFoundException(String message) {
        super(message);
    }
}
