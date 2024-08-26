package com.example.electricStation.repository.impl;

import com.example.electricStation.common.CommonResponse;
import com.example.electricStation.dto.ElectricStation;
import com.example.electricStation.dto.SingleResponse;
import com.example.electricStation.dto.ListResponse;
import com.example.electricStation.repository.ElecStationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ElecStationServiceImpl implements ElecStationService {

    @Value("${station.api.key}")
    private String SERVICE_KEY;

    private static final String BASE_URL = "http://openapi.kepco.co.kr/service/EvInfoServiceV2/getEvSearchList";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

//    @Override
//    public String getElecStation(String location) {
//        String url = BASE_URL + "?serviceKey=" + SERVICE_KEY + "&pageNo=1&numOfRows=10&addr=" + location;
//        try {
//            // GET 요청을 보내고, 응답을 String으로 받음
//            String response = restTemplate.getForObject(url, String.class);
//            return response;
//        } catch (Exception e) {
//            // 예외 처리 (필요에 따라 수정 가능)
//            e.printStackTrace();
//            return "Failed to retrieve data";
//        }
//    }

    @Override
    public JsonNode getElecStation(String location) {
        String url = BASE_URL + "?serviceKey=" + SERVICE_KEY + "&pageNo=1&numOfRows=10&addr=" + location;
        try {
            // GET 요청을 보내고, 응답을 String으로 받음
            String response = restTemplate.getForObject(url, String.class);
            JsonNode jsonResponse = objectMapper.readTree(response);

            return jsonResponse;
        } catch (Exception e) {
            // 예외 처리 (필요에 따라 수정 가능)
            e.printStackTrace();
            return objectMapper.createObjectNode().put("error", "Failed to retrieve data");
        }
    }

    public List<ElectricStation> getElectricStationsFromJson(JsonNode jsonResponse) {
        List<ElectricStation> electricStations = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Navigate to the "item" array in the JSON response
        JsonNode items = jsonResponse.path("response").path("body").path("items").path("item");

        if (items.isArray()) {
            for (JsonNode item : items) {
                ElectricStation station = ElectricStation.of(
                        item.path("addr").asText(),
                        item.path("chargeTp").asLong(),
                        item.path("cpId").asLong(),
                        item.path("cpNm").asText(),
                        item.path("cpStat").asLong(),
                        item.path("cpTp").asLong(),
                        item.path("csId").asLong(),
                        item.path("csNm").asText(),
                        item.path("lat").asDouble(),
                        item.path("longi").asDouble(),
                        LocalDateTime.parse(item.path("statUpdateDatetime").asText(), formatter)
                );
                electricStations.add(station);
            }
        }
        return electricStations;
    }

}
