package com.example.electricStation.controller;

import com.example.electricStation.common.CommonResponseService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @DisplayName("즐겨찾기 추가 - positive 케이스")
    @Test
    void setFavoriteTest() throws Exception {
        // given
        ElecStationResponseDto responseDto = ElecStationResponseDto.builder()
                .csId(1L)
                .addr("Street1")
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
                .andExpect(jsonPath("$.data.csId").value(1L))
                .andExpect(jsonPath("$.data.addr").value("Street1"));
    }

    @Test
    @DisplayName("즐겨찾기 추가 - negative 케이스")
    void setFavoriteTestNegative() throws Exception {
        // given
        given(elecStationService.setFavorite(anyLong(), anyString()))
                .willThrow(new NotFoundException("User Not Found"));

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/charging-stations/1/favorites")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("즐겨찾기 삭제 - positive 케이스")
    void removeFavoriteTestPositive() throws Exception {
        // given
        ElecStationResponseDto responseDto = ElecStationResponseDto.builder()
                .csId(1L)
                .addr("Some Address")
                .build();

        given(elecStationService.deleteFavorite(anyLong(), anyString())).willReturn(responseDto);
        SingleResponse<ElecStationResponseDto> singleResponse = new SingleResponse<>();
        singleResponse.setCode(200);
        singleResponse.setData(responseDto);
        given(commonResponseService.getSingleResponse(responseDto)).willReturn(singleResponse);

        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/charging-stations/1/favorites")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.csId").value(1L))
                .andExpect(jsonPath("$.data.addr").value("Some Address"));
    }

    @Test
    @DisplayName("즐겨찾기 삭제 - Negative 케이스")
    void removeFavoriteNegativeTest() throws Exception {
        // given
        given(elecStationService.deleteFavorite(anyLong(), anyString())).willThrow(new NotFoundException("해당하는 즐겨찾기가 존재하지 않습니다."));

        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/charging-stations/1/favorites")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound());
    }
}