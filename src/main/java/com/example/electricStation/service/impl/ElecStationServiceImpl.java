package com.example.electricStation.service.impl;

import com.example.electricStation.dto.ElecStationResponseDto;
import com.example.electricStation.dto.ElectricStation;
import com.example.electricStation.entity.Favorites;
import com.example.electricStation.entity.User;
import com.example.electricStation.exception.NotFoundException;
import com.example.electricStation.repository.FavoritesRepository;
import com.example.electricStation.repository.UserRepository;
import com.example.electricStation.service.ElecStationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Slf4j
@Service
public class ElecStationServiceImpl implements ElecStationService {

    @Value("${station.api.key}")
    private String SERVICE_KEY;

    private static final String BASE_URL = "http://openapi.kepco.co.kr/service/EvInfoServiceV2/getEvSearchList";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final FavoritesRepository favoritesRepository;
    private final UserRepository userRepository;

    @Override
    public JsonNode getElecStation(String location) {
        String url = BASE_URL + "?serviceKey=" + SERVICE_KEY + "&pageNo=1&numOfRows=10&addr=" + location;
        try {
            // GET 요청을 보내고, 응답을 String으로 받음
            String response = restTemplate.getForObject(url, String.class);

            return objectMapper.readTree(response);
        } catch (Exception e) {
            // 예외 처리 (필요에 따라 수정 가능)
            return objectMapper.createObjectNode().put("error", "Failed to retrieve data");
        }
    }

    public List<ElectricStation> getElectricStationsFromJson(JsonNode jsonResponse) {
        // Navigate to the "item" array in the JSON response
        JsonNode items = jsonResponse.path("response").path("body").path("items").path("item");

        if (items.isArray()) {
            return StreamSupport.stream(items.spliterator(), false)
                    .map(ElectricStation::of)
                    .toList();
        } else {
            throw new IllegalStateException("Server Error");
        }
    }

    @Override
    public ElecStationResponseDto setFavorite(Long stationId, String userName) {
        // todo: 존재하는 stationId인지 validation 로직 추가

        User findUser = validateUser(userName);

        Favorites findFavorites = validateFavorite(stationId, findUser);
        if(findFavorites != null) {
            return ElecStationResponseDto.builder()
                    .csId(findFavorites.getStationId())
                    .build();
        }

        Favorites favorites = Favorites.builder()
                .user(findUser)
                .stationId(stationId)
                .build();

        favoritesRepository.save(favorites);
        return ElecStationResponseDto.builder()
                .csId(stationId)
                .build();
    }

    @Override
    public ElecStationResponseDto deleteFavorite(Long stationId, String userName) {
        // todo: 존재하는 stationId인지 validation 로직 추가

        User findUser = validateUser(userName);
        Favorites findFavorites = validateFavorite(stationId, findUser);
        if(findFavorites == null) {
            throw new NotFoundException("해당하는 즐겨찾기가 존재하지 않습니다.");
        }

        favoritesRepository.delete(findFavorites);

        return ElecStationResponseDto.builder()
                .csId(stationId)
                .build();
    }

    private Favorites validateFavorite(Long stationId, User findUser) {
        return favoritesRepository.findByUserIdAndStationId(findUser.getId(), stationId)
                .orElse(null);
    }

    private User validateUser(String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new NotFoundException("User Not Found"));
    }
}