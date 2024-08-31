package com.example.electricStation.service;

import com.example.electricStation.dto.ElecStationResponseDto;
import com.example.electricStation.entity.Favorites;
import com.example.electricStation.entity.User;
import com.example.electricStation.exception.ErrorMsg;
import com.example.electricStation.exception.StationNotFoundException;
import com.example.electricStation.exception.UserNotFoundException;
import com.example.electricStation.repository.FavoritesRepository;
import com.example.electricStation.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ElecStationServiceTest {

    @Autowired
    ElecStationService elecStationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FavoritesRepository favoritesRepository;

    @BeforeEach
    void setUp() {
        elecStationService.getElecStation("서울특별시 중구");
//        elecStationService.getElectricStationsFromJson(jsonNode);
    }

    @DisplayName("즐겨찾기 추가 - positive 케이스")
    @Test
    void setFavorite() throws Exception {
        // given
        User user = User.builder()
                .id(1L)
                .username("userA")
                .password("123456")
                .build();
        userRepository.save(user);

        Long stationId = 14L;

        // when
        ElecStationResponseDto elecStationResponseDto = elecStationService.setFavorite(stationId, user.getUsername());

        // then
        assertThat(elecStationResponseDto.getCsId()).isEqualTo(stationId);
        assertThat(elecStationResponseDto.getAddr()).isEqualTo("서울특별시 중구 남대문로 92 1층 주차장");
    }

    @DisplayName("즐겨찾기 추가 - negative 케이스")
    @Test
    void 존재하지_않는_회원으로_추가() throws Exception {
        // given
        String userName = "dummyUser";
        Long stationId = 14L;

        // when
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                elecStationService.setFavorite(stationId, userName));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorMsg.USER_NOT_FOUND_EXCEPTION);
    }

    @DisplayName("즐겨찾기 추가 - negative 케이스")
    @Test
    void 존재하지_않는_주유소로_추가() throws Exception {
        // given
        User user = User.builder()
                .id(1L)
                .username("userA")
                .password("123456")
                .build();
        userRepository.save(user);

        Long stationId = -1L;

        // when
        StationNotFoundException exception = assertThrows(StationNotFoundException.class, () ->
                elecStationService.setFavorite(stationId, user.getUsername()));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorMsg.STATION_NOT_FOUND_EXCEPTION);
    }

    @DisplayName("즐겨찾기 삭제 - positive 케이스")
    @Test
    void 즐겨찾기_삭제() throws Exception {
        // given
        Long stationId = 14L;
        User user = User.builder()
                .id(1L)
                .username("userA")
                .password("123456")
                .build();
        userRepository.save(user);

        Favorites favorites = Favorites.builder()
                .id(1L)
                .stationId(stationId)
                .user(user)
                .addr("서울특별시 중구 남대문로 92 1층 주차장")
                .build();
        favoritesRepository.save(favorites);

        // when
        ElecStationResponseDto elecStationResponseDto = elecStationService.deleteFavorite(stationId, user.getUsername());

        // then
        assertThat(elecStationResponseDto.getCsId()).isEqualTo(stationId);
        assertThat(elecStationResponseDto.getAddr()).isEqualTo("서울특별시 중구 남대문로 92 1층 주차장");
    }

    @DisplayName("즐겨찾기 삭제 - negative 케이스")
    @Test
    void 존재하지_않는_회원으로_즐겨찾기_삭제() throws Exception {
        // given
        Long stationId = 14L;
        String userName = "dummyUser";
        User user = User.builder()
                .id(1L)
                .username("userA")
                .password("123456")
                .build();
        userRepository.save(user);

        Favorites favorites = Favorites.builder()
                .id(1L)
                .stationId(stationId)
                .user(user)
                .addr("서울특별시 중구 남대문로 92 1층 주차장")
                .build();
        favoritesRepository.save(favorites);

        // when
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                elecStationService.setFavorite(stationId, userName));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorMsg.USER_NOT_FOUND_EXCEPTION);
    }

    @DisplayName("즐겨찾기 삭제 - negative 케이스")
    @Test
    void 존재하지_않는_충전소로_즐겨찾기_삭제() throws Exception {
        // given
        Long stationId = -1L;
        User user = User.builder()
                .id(1L)
                .username("userA")
                .password("123456")
                .build();
        userRepository.save(user);

        // when
        StationNotFoundException exception = assertThrows(StationNotFoundException.class, () ->
                elecStationService.setFavorite(stationId, user.getUsername()));

        // then
        assertThat(exception.getMessage()).isEqualTo(ErrorMsg.STATION_NOT_FOUND_EXCEPTION);
    }
}