package com.example.electricStation.controller;

import com.example.electricStation.common.CommonResponseService;
import com.example.electricStation.dto.ElectricStation;
import com.example.electricStation.dto.ListResponse;
import com.example.electricStation.repository.ElecStationService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/charging-stations")
@RestController
public class ElecStationController {

    private final ElecStationService elecStationService;
    private final CommonResponseService commonResponseService;

//    @GetMapping("/nearby")
//    public ResponseEntity<String> nearBy(@RequestParam(required = false) String location) {
//
//        if(location == null){
//            location = "서울특별시 중구";
//        }
//        String elecStation = elecStationService.getElecStation(location);
//
//        return ResponseEntity.ok(elecStation);
//    }

    @GetMapping("/nearby")
    public ListResponse<ElectricStation> nearBy(@RequestParam(required = false) String location) {

        if(location == null){
            location = "서울특별시 중구";
        }
        JsonNode elecStation = elecStationService.getElecStation(location);
        List<ElectricStation> electricStations = elecStationService.getElectricStationsFromJson(elecStation);

        return commonResponseService.getListResponse(electricStations);
    }
}
