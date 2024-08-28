package com.example.electricStation.service;

import com.example.electricStation.dto.ElecStationResponseDto;
import com.example.electricStation.dto.ElectricStation;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface ElecStationService {

    public JsonNode getElecStation(String location);
    public List<ElectricStation> getElectricStationsFromJson(JsonNode jsonResponse);
    public List<ElectricStation> getElectricStationsByCsId(Long csId);

    ElecStationResponseDto setFavorite(Long stationId, String userName);

    ElecStationResponseDto deleteFavorite(Long stationId, String userName);
}
