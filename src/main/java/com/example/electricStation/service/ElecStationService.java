package com.example.electricStation.service;

import com.example.electricStation.dto.ElecStationDetailsResponseDto;
import com.example.electricStation.dto.ElecStationResponseDto;
import com.example.electricStation.dto.ElectricStation;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface ElecStationService {

    public List<ElectricStation> getElecStation(String location);
    public List<ElectricStation> getElectricStationsByCsId(Long csId);
    ElecStationResponseDto setFavorite(Long stationId);
    ElecStationResponseDto deleteFavorite(Long stationId);
    List<ElecStationResponseDto> getFavorite();
    List<ElecStationDetailsResponseDto> getFavoriteDetails(Long stationId, String location);
}
