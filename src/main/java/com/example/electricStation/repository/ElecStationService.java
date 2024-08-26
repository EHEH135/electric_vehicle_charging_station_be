package com.example.electricStation.repository;

import com.example.electricStation.dto.ElectricStation;
import com.example.electricStation.dto.SingleResponse;
import com.example.electricStation.dto.ListResponse;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface ElecStationService {

    public JsonNode getElecStation(String location);
    public List<ElectricStation> getElectricStationsFromJson(JsonNode jsonResponse);
//    public String getElecStation(String location);
}
