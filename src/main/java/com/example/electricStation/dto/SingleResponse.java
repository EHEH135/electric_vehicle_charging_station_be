package com.example.electricStation.dto;

import com.example.electricStation.common.CommonResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResponse<T> extends CommonResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;
}
