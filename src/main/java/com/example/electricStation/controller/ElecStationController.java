package com.example.electricStation.controller;

import com.example.electricStation.common.CommonResponseService;
import com.example.electricStation.dto.ElecStationResponseDto;
import com.example.electricStation.dto.ElectricStation;
import com.example.electricStation.dto.ListResponse;
import com.example.electricStation.dto.SingleResponse;
import com.example.electricStation.service.ElecStationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "전기차 충전소 관련 API", description = "RestAPI 관련 명세")
@RequiredArgsConstructor
@RequestMapping("/api/v1/charging-stations")
@RestController
public class ElecStationController {

    private final ElecStationService elecStationService;
    private final CommonResponseService commonResponseService;

    @Operation(summary = "위치 기반 전기차 충전소 정보 조회",
            description = "사용자로부터 전달받은 위치를 기반으로 주변 전기차 충전소 정보를 조회한다. - default 위치 서울특별시 중구")
    @GetMapping("/nearby")
    public ListResponse<ElectricStation> nearBy(@RequestParam(required = false) String location) {

        if(location == null){
            location = "서울특별시 중구";
        }
        List<ElectricStation> electricStations = elecStationService.getElecStation(location);

        return commonResponseService.getListResponse(electricStations);
    }

    @Operation(summary = "전기차 충전소 상세 정보 조회", description = "사용자가 선택한 전기차 충전소의 상세 정보를 조회한다.")
    @GetMapping("/details")
    public ListResponse<ElectricStation> details(@RequestParam Long csId) {
        List<ElectricStation> stationDetails = elecStationService.getElectricStationsByCsId(csId);
        return commonResponseService.getListResponse(stationDetails);
    }

    @Operation(summary = "충전소ID로 즐겨찾기 등록", description = "사용자로부터 전달받은 충전소ID로 즐겨찾기에 등록한다.")
    @PostMapping("/{stationId}/favorites")
    public SingleResponse<ElecStationResponseDto> setFavorite(@PathVariable Long stationId) {
        // todo Principal 방식으로 현재 사용자 정보 가져오기
        String userName = "userA";
        ElecStationResponseDto elecStationResponseDto = elecStationService.setFavorite(stationId, userName);

        return commonResponseService.getSingleResponse(elecStationResponseDto);
    }

    @Operation(summary = "충전소ID로 즐겨찾기 삭제", description = "사용자로부터 전달받은 충전소ID로 즐겨찾기에 등록한다.")
    @DeleteMapping("/{stationId}/favorites")
    public SingleResponse<ElecStationResponseDto> removeFavorite(@PathVariable Long stationId) {
        // todo Principal 방식으로 현재 사용자 정보 가져오기
        String userName = "userA";
        ElecStationResponseDto elecStationResponseDto = elecStationService.deleteFavorite(stationId, userName);

        return commonResponseService.getSingleResponse(elecStationResponseDto);
    }

    @Operation(summary = "충전소ID로 즐겨찾기 조회", description = "사용자가 저장한 즐겨찾기 충전소ID를 가져온다..")
    @GetMapping("favorites")
    public ListResponse<ElecStationResponseDto> getFavorite() {
        // todo Principal 방식으로 현재 사용자 정보 가져오기
        String userName = "userA";
        List<ElecStationResponseDto> elecStationResponseDto = elecStationService.getFavorite(userName);
        return commonResponseService.getListResponse(elecStationResponseDto);
    }
}
