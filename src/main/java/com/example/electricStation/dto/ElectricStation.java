package com.example.electricStation.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ElectricStation {
    private String addr;          // 주소
    private Long chargeTp;         // 충전 타입 (1: 완속, 2: 급속)
    private Long cpId;             // 충전기 ID
    private String cpNm;          // 충전기 이름
    private Long cpStat;           // 충전기 상태 (1: 사용 가능, 2: 사용 중, 3: 고장, 4: 점검 중)
    private Long cpTp;             // 충전기 타입 (5: DC차데모, 7: AC완속, 10: DC콤보 등)
    private Long csId;             // 충전소 ID
    private String csNm;          // 충전소 이름
    private double lat;           // 위도
    private double longi;         // 경도
    private LocalDateTime statUpdateDatetime; // 상태 업데이트 시간

    // Static factory method
    public static ElectricStation of(JsonNode item) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ElectricStation station = new ElectricStation();
        station.setAddr(item.path("addr").asText());
        station.setChargeTp(item.path("chargeTp").asLong());
        station.setCpId(item.path("cpId").asLong());
        station.setCpNm(item.path("cpNm").asText());
        station.setCpStat(item.path("cpStat").asLong());
        station.setCpTp(item.path("cpTp").asLong());
        station.setCsId(item.path("csId").asLong());
        station.setCsNm(item.path("csNm").asText());
        station.setLat(item.path("lat").asDouble());
        station.setLongi(item.path("longi").asDouble());
        station.setStatUpdateDatetime(LocalDateTime.parse(item.path("statUpdateDatetime").asText(), formatter));
        return station;
    }
}
