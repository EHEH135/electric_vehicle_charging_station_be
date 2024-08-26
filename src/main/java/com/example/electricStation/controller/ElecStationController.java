package com.example.electricStation.controller;

import com.example.electricStation.repository.ElecStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/charging-stations")
@RestController
public class ElecStationController {

    private final ElecStationService elecStationService;

    @GetMapping("/nearby")
    public ResponseEntity<String> nearBy(@RequestParam(required = false) String location) {

        if(location == null){
            location = "서울특별시 중구";
        }
        String elecStation = elecStationService.getElecStation(location);

        return ResponseEntity.ok(elecStation);
    }
}
