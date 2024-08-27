package com.example.electricStation.common;

import com.example.electricStation.dto.ListResponse;
import com.example.electricStation.dto.SingleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommonResponseService {

    public <T> SingleResponse<T> getSingleResponse(T data) {
        SingleResponse<T> singleResponse = new SingleResponse<>();
        singleResponse.setData(data);
        setSuccessResponse(singleResponse);

        log.info("SingleResponse 생성: {}", data);

        return singleResponse;
    }

    public <T> ListResponse<T> getListResponse(List<T> dataList) {
        ListResponse<T> listResponse = new ListResponse<>();
        listResponse.setDataList(dataList);
        setSuccessResponse(listResponse);

        return listResponse;
    }

    public <T> ListResponse<T> getErrorResponse(List<T> dataList, String errorMsg) {
        ListResponse<T> listResponse = new ListResponse<>();
        listResponse.setDataList(dataList);
        setErrorResponse(listResponse, errorMsg);

        return listResponse;
    }

    public void setSuccessResponse(CommonResponse commonResponse) {
        commonResponse.setCode(HttpStatus.OK.value());
    }

    public void setErrorResponse(CommonResponse commonResponse, String errorMessage) {
        log.error("Error occurred: {}", errorMessage);
        commonResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
