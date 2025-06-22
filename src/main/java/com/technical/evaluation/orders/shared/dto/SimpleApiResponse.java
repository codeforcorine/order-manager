package com.technical.evaluation.orders.shared.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
public class SimpleApiResponse extends ApiResponse implements Serializable {
    public SimpleApiResponse(String code, String message) {
        super(code, message);
    }

    public SimpleApiResponse(String message){
        super(ApiResponseCode.SUCCESS.getLabel(), message);
    }

    @JsonIgnore
    @Override
    public Object getData() {
        return super.getData();
    }
}
