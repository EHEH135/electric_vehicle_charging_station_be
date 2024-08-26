package com.example.electricStation.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse {
    boolean success;
    int code;
    String message;
}
