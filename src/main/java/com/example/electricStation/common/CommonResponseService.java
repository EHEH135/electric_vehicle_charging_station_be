package com.example.electricStation.common;

import com.example.electricStation.dto.ListResponse;
import com.example.electricStation.dto.SingleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommonResponseService {

    public <T> SingleResponse<T> getSingleResponse(T data) {
        SingleResponse singleResponse = new SingleResponse();
        singleResponse.setData(data);
        setSuccessResponse(singleResponse);

        return singleResponse;
    }

    public <T> ListResponse<T> getListResponse(List<T> dataList) {
        ListResponse listResponse = new ListResponse();
        listResponse.setDataList(dataList);
        setSuccessResponse(listResponse);

        return listResponse;
    }

    public void setSuccessResponse(CommonResponse commonResponse) {
        commonResponse.setCode(0);
        commonResponse.setSuccess(true);
        commonResponse.setMessage("SUCCESS");
    }
}
