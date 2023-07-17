package com.eleven.miniproject.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusCodeDto {

    private int statusCode;
    private String responseMessage;

    public StatusCodeDto(int statusCode, String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
    }

}