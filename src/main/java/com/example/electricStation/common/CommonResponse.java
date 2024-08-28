package com.example.electricStation.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse {
    int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;
}
