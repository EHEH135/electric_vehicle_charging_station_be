package com.example.electricStation.controller;

import com.example.electricStation.common.CommonResponseService;
import com.example.electricStation.dto.ElecStationResponseDto;
import com.example.electricStation.dto.SingleResponse;
import com.example.electricStation.exception.NotFoundException;
import com.example.electricStation.service.ElecStationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(ElecStationController.class)
class ElecStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ElecStationService elecStationService;

    @MockBean
    private CommonResponseService commonResponseService;

    @DisplayName("즐겨찾기 추가 - positive 케이스")
    @Test
    void setFavoriteTest() throws Exception {
        // given
        ElecStationResponseDto responseDto = ElecStationResponseDto.builder()
                .csId(1L)
                .build();

        given(elecStationService.setFavorite(anyLong(), anyString())).willReturn(responseDto);
        SingleResponse<ElecStationResponseDto> singleResponse = new SingleResponse<>();
        singleResponse.setCode(200);
        singleResponse.setData(responseDto);
        given(commonResponseService.getSingleResponse(responseDto)).willReturn(singleResponse);

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/charging-stations/1/favorites")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.csId").value(1L));
    }

    @Test
    @DisplayName("즐겨찾기 추가 - negative 케이스")
    void setFavoriteTestNegative() throws Exception {
        // given
        given(elecStationService.setFavorite(anyLong(), anyString()))
                .willThrow(new NotFoundException("User Not Found"));

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/charging-stations/1/favorites")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        result.andExpect(status().isNotFound());
    }
}