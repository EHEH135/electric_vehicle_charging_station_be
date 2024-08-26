package com.example.electricStation.dto;

import com.example.electricStation.common.CommonResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResponse<T> extends CommonResponse {
    List<T> dataList;
}
