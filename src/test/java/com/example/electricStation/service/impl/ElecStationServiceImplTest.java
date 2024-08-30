package com.example.electricStation.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.eq;

class ElecStationServiceImplTest {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ElecStationServiceImpl elecStationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getElecStation() throws Exception {
        // given
//        String mockResponse = "{\"response\":{\"body\":{\"items\":{\"item\":[]}}}}";
//        String mockResponse = "{\"response\":{\"body\":{\"items\":{\"item\":[{\"addr\":\"서울특별시 중구 남대문로 92 1층 주차장\",\"chargeTp\":2,\"cpId\":811,\"cpNm\":\"급속01\",\"cpStat\":1,\"cpTp\":10,\"csId\":14,\"csNm\":\"서울직할\",\"lat\":37.565199,\"longi\":126.983339,\"statUpdateDatetime\":\"2024-08-29 18:34:45\"}]},\"numOfRows\":10,\"pageNo\":1,\"totalCount\":312}}}";
//        JsonNode mockJsonNode = objectMapper.readTree(mockResponse);
//
//        given(restTemplate.getForObject(anyString(), eq(String.class))).willReturn(mockResponse);
//        given(objectMapper.readTree(mockResponse)).willReturn(mockJsonNode);
//
//        // when
//        JsonNode result = elecStationService.getElecStation("서울특별시 중구");
//
//        // then
//        assertNotNull(result);
//        assertNotNull(result.path("response").path("body").path("items").path("item"));
    }

    @Test
    void getElectricStationsFromJson() {
    }

    @Test
    void getElectricStationsByCsId() {
    }
}