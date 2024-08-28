package com.example.electricStation.dto;

import com.example.electricStation.common.CommonResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResponse<T> extends CommonResponse {
    T data;
}
