package com.technical.evaluation.orders.shared.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class ApiResponse {

    @Setter(AccessLevel.NONE)
    private String code;
    private String message;
    private Object data;

    public void setCode(String code) {
        this.code = code;
    }

    public ApiResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
