package com.example.electricStation.repository.impl;

import com.example.electricStation.repository.ElecStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
@Service
public class ElecStationServiceImpl implements ElecStationService {

    @Value("${station.api.key}")
    private String SERVICE_KEY;

    private static final String BASE_URL = "http://openapi.kepco.co.kr/service/EvInfoServiceV2/getEvSearchList";
    private final RestTemplate restTemplate;

    @Override
    public String getElecStation(String location) {
        String url = BASE_URL + "?serviceKey=" + SERVICE_KEY + "&pageNo=1&numOfRows=10&addr=" + location;
        try {
            // GET 요청을 보내고, 응답을 String으로 받음
            String response = restTemplate.getForObject(url, String.class);
            return response;
        } catch (Exception e) {
            // 예외 처리 (필요에 따라 수정 가능)
            e.printStackTrace();
            return "Failed to retrieve data";
        }
    }
}
