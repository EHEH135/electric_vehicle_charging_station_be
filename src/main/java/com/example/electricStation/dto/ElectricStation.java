package com.example.electricStation.dto;

import lombok.Data;

import java.time.LocalDateTime;

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
    public static ElectricStation of(String addr, Long chargeTp, Long cpId, String cpNm, Long cpStat, Long cpTp, Long csId, String csNm, double lat, double longi, LocalDateTime statUpdateDatetime) {
        ElectricStation station = new ElectricStation();
        station.setAddr(addr);
        station.setChargeTp(chargeTp);
        station.setCpId(cpId);
        station.setCpNm(cpNm);
        station.setCpStat(cpStat);
        station.setCpTp(cpTp);
        station.setCsId(csId);
        station.setCsNm(csNm);
        station.setLat(lat);
        station.setLongi(longi);
        station.setStatUpdateDatetime(statUpdateDatetime);
        return station;
    }
}
