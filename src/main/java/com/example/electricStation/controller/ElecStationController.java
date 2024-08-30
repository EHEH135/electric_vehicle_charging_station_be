package com.example.electricStation.controller;

import com.example.electricStation.common.CommonResponseService;
import com.example.electricStation.dto.ElecStationResponseDto;
import com.example.electricStation.dto.ElectricStation;
import com.example.electricStation.dto.ListResponse;
import com.example.electricStation.dto.SingleResponse;
import com.example.electricStation.service.ElecStationService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/nearby")
    public ListResponse<ElectricStation> nearBy(@RequestParam(required = false) String location) {

        if(location == null){
            location = "서울특별시 중구";
        }
        JsonNode elecStation = elecStationService.getElecStation(location);
        List<ElectricStation> electricStations = elecStationService.getElectricStationsFromJson(elecStation);

        return commonResponseService.getListResponse(electricStations);
    }

    @GetMapping("/details")
    public ListResponse<ElectricStation> details(@RequestParam Long csId) {
        List<ElectricStation> stationDetails = elecStationService.getElectricStationsByCsId(csId);
        return commonResponseService.getListResponse(stationDetails);
    }

    @PostMapping("/{stationId}/favorites")
    public SingleResponse<ElecStationResponseDto> setFavorite(@PathVariable Long stationId) {
        // todo Principal 방식으로 현재 사용자 정보 가져오기
        String userName = "userA";
        ElecStationResponseDto elecStationResponseDto = elecStationService.setFavorite(stationId, userName);

        return commonResponseService.getSingleResponse(elecStationResponseDto);
    }

    @DeleteMapping("/{stationId}/favorites")
    public SingleResponse<ElecStationResponseDto> removeFavorite(@PathVariable Long stationId) {
        // todo Principal 방식으로 현재 사용자 정보 가져오기
        String userName = "userA";
        ElecStationResponseDto elecStationResponseDto = elecStationService.deleteFavorite(stationId, userName);

        return commonResponseService.getSingleResponse(elecStationResponseDto);
    }
}
