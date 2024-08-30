package com.example.electricStation.controller;

import com.example.electricStation.common.CommonResponseService;
import com.example.electricStation.dto.ElectricStation;
import com.example.electricStation.dto.ListResponse;
import com.example.electricStation.service.ElecStationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.example.electricStation.dto.ElecStationResponseDto;
import com.example.electricStation.dto.SingleResponse;
import com.example.electricStation.exception.NotFoundException;
import com.example.electricStation.service.ElecStationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(ElecStationController.class)
class ElecStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ElecStationService elecStationService;

    @MockBean
    private CommonResponseService commonResponseService;

    @DisplayName("주변 검색 - positive 케이스")
    @Test
    void nearByTestPositive() throws Exception {
        // given
        ElectricStation station1 = new ElectricStation();
        station1.setAddr("서울특별시 중구 남대문로 92 1층 주차장");
        station1.setCpId(811L);
        station1.setCpNm("급속01");
        station1.setCsId(14L);
        station1.setCsNm("서울직할");

        ElectricStation station2 = new ElectricStation();
        station2.setAddr("서울특별시 중구 남대문로 92 1층 주차장");
        station2.setCpId(6659L);
        station2.setCpNm("급속02");
        station2.setCsId(14L);
        station2.setCsNm("서울직할");

        List<ElectricStation> stations = List.of(station1, station2);

        ListResponse<ElectricStation> listResponse = new ListResponse<>();
        listResponse.setCode(200);
        listResponse.setDataList(stations);

        given(elecStationService.getElecStation(anyString())).willReturn(JsonNodeFactory.instance.objectNode());  // Adjust if needed
        given(elecStationService.getElectricStationsFromJson(any())).willReturn(stations);
        given(commonResponseService.getListResponse(stations)).willReturn(listResponse);

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/charging-stations/nearby")
                .contentType(MediaType.APPLICATION_JSON)
                .param("location", "서울특별시 중구"));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.dataList[0].addr").value("서울특별시 중구 남대문로 92 1층 주차장"))
                .andExpect(jsonPath("$.dataList[0].cpId").value(811))
                .andExpect(jsonPath("$.dataList[0].cpNm").value("급속01"))
                .andExpect(jsonPath("$.dataList[0].csId").value(14))
                .andExpect(jsonPath("$.dataList[0].csNm").value("서울직할"));
    }

    @DisplayName("주변 검색 - negative 케이스")
    @Test
    void nearByTestNegative() throws Exception {

    }

    @DisplayName("상세정보 조회 - positive 케이스")
    @Test
    void detailInfoTestPositive() throws Exception {
        // given
        ElectricStation station1 = new ElectricStation();
        station1.setAddr("서울특별시 중구 남대문로 92 1층 주차장");
        station1.setCpId(811L);
        station1.setCpNm("급속01");
        station1.setCsId(14L);
        station1.setCsNm("서울직할");

        ElectricStation station2 = new ElectricStation();
        station2.setAddr("서울특별시 중구 남대문로 92 1층 주차장");
        station2.setCpId(6659L);
        station2.setCpNm("급속02");
        station2.setCsId(14L);
        station2.setCsNm("서울직할");

        List<ElectricStation> stations = List.of(station1, station2);
        ListResponse<ElectricStation> listResponse = new ListResponse<>();
        listResponse.setCode(200);
        listResponse.setDataList(stations);

        // Mock the service call for details data
        given(elecStationService.getElectricStationsByCsId(14L)).willReturn(stations);
        given(commonResponseService.getListResponse(stations)).willReturn(listResponse);

        // when: call detail API for csId 14
        ResultActions result = mockMvc.perform(get("/api/v1/charging-stations/details")
                .param("csId", "14")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.dataList[0].addr").value("서울특별시 중구 남대문로 92 1층 주차장"))
                .andExpect(jsonPath("$.dataList[0].cpId").value(811))
                .andExpect(jsonPath("$.dataList[0].cpNm").value("급속01"))
                .andExpect(jsonPath("$.dataList[0].csId").value(14))
                .andExpect(jsonPath("$.dataList[0].csNm").value("서울직할"));
    }

    @DisplayName("상세 정보 검색 - negative 케이스")
    @Test
    void detailsTestNegative() throws Exception {

    }
}