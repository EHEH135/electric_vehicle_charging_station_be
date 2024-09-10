package com.example.electricStation.dto;

import com.example.electricStation.exception.ErrorMsg;
import com.example.electricStation.exception.StationNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ElecStationDetailsResponseDto {
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
    public static ElecStationDetailsResponseDto of(JsonNode item) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ElecStationDetailsResponseDto.builder()
                .addr(item.path("addr").asText())
                .chargeTp(item.path("chargeTp").asLong())
                .cpId(item.path("cpId").asLong())
                .cpNm(item.path("cpNm").asText())
                .cpStat(item.path("cpStat").asLong())
                .cpTp(item.path("cpTp").asLong())
                .csId(item.path("csId").asLong())
                .csNm(item.path("csNm").asText())
                .lat(item.path("lat").asDouble())
                .longi(item.path("longi").asDouble())
                .statUpdateDatetime(LocalDateTime.parse(item.path("statUpdateDatetime").asText(), formatter))
                .build();
    }

    public boolean isSameStationId(Long stationId) {
        return Objects.equals(this.csId, stationId);
    }
}
