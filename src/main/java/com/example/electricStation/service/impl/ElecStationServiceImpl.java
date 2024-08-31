package com.example.electricStation.service.impl;

import com.example.electricStation.dto.ElecStationResponseDto;
import com.example.electricStation.dto.ElectricStation;
import com.example.electricStation.entity.Favorites;
import com.example.electricStation.entity.User;
import com.example.electricStation.exception.ApiServerException;
import com.example.electricStation.exception.BookMarkNotFoundException;
import com.example.electricStation.exception.ErrorMsg;
import com.example.electricStation.exception.StationNotFoundException;
import com.example.electricStation.exception.UserNotFoundException;
import com.example.electricStation.repository.FavoritesRepository;
import com.example.electricStation.repository.UserRepository;
import com.example.electricStation.service.ElecStationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
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
    private static final List<ElectricStation> electricStations = new CopyOnWriteArrayList<>();

    @Override
    public JsonNode getElecStation(String location) {
        healthCheck();

        String url = BASE_URL + "?serviceKey=" + SERVICE_KEY + "&pageNo=1&numOfRows=10&addr=" + location;
        try {
            String response = restTemplate.getForObject(url, String.class);

            return objectMapper.readTree(response);
        } catch (Exception e) {
            return objectMapper.createObjectNode().put("error", "Failed to retrieve data");
        }
    }

    public List<ElectricStation> getElectricStationsFromJson(JsonNode jsonResponse) {
        // Navigate to the "item" array in the JSON response
        JsonNode items = jsonResponse.path("response").path("body").path("items").path("item");
        electricStations.clear();

        if (items.isArray()) {
            electricStations.addAll(StreamSupport.stream(items.spliterator(), false)
                    .map(ElectricStation::of)
                    .toList());

            return electricStations;
        } else {
            throw new IllegalStateException("Server Error");
        }
    }

    @Override
    public List<ElectricStation> getElectricStationsByCsId(Long csId) {
        return electricStations.stream()
                .filter(station -> station.getCsId().equals(csId))
                .toList();
    }

    @Override
    public ElecStationResponseDto setFavorite(Long stationId, String userName) {
        ElectricStation findStation = validateStationId(stationId);

        User findUser = validateUser(userName);

        Favorites findFavorites = validateFavorite(stationId, findUser);
        if(findFavorites != null) {
            return ElecStationResponseDto.builder()
                    .csId(findFavorites.getStationId())
                    .addr(findFavorites.getAddr())
                    .build();
        }

        Favorites favorites = Favorites.builder()
                .user(findUser)
                .stationId(stationId)
                .addr(findStation.getAddr())
                .build();

        Favorites savedFavorites = favoritesRepository.save(favorites);
        return ElecStationResponseDto.builder()
                .csId(savedFavorites.getStationId())
                .addr(savedFavorites.getAddr())
                .build();
    }

    @Override
    public ElecStationResponseDto deleteFavorite(Long stationId, String userName) {
        ElectricStation findStation = validateStationId(stationId);

        User findUser = validateUser(userName);
        Favorites findFavorites = validateFavorite(stationId, findUser);
        if(findFavorites == null) {
            throw new BookMarkNotFoundException(ErrorMsg.BOOKMARK_NOT_FOUND_EXCEPTION);
        }

        favoritesRepository.delete(findFavorites);

        return ElecStationResponseDto.builder()
                .csId(stationId)
                .addr(findStation.getAddr())
                .build();
    }

    private ElectricStation validateStationId(Long stationId) {
        return electricStations.stream()
                .filter(station -> station.getCsId().equals(stationId))
                .findFirst()
                .orElseThrow(() -> new StationNotFoundException(ErrorMsg.STATION_NOT_FOUND_EXCEPTION));
    }

    private Favorites validateFavorite(Long stationId, User findUser) {
        return favoritesRepository.findByUserIdAndStationId(findUser.getId(), stationId)
                .orElse(null);
    }

    private User validateUser(String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException(ErrorMsg.USER_NOT_FOUND_EXCEPTION));
    }

    private void healthCheck() {
        String url = BASE_URL + "?serviceKey=" + SERVICE_KEY + "&pageNo=1&numOfRows=10&addr=서울특별시 중구";
        String resultCode = "";
        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode jsonResponse = objectMapper.readTree(response);
            resultCode = jsonResponse.path("response").path("header").path("resultCode").asText();
            if (!resultCode.equals("00")) {
                throw new ApiServerException(ErrorMsg.API_SERVER_EXCEPTION);
            }
        } catch (Exception e) {
            throw new ApiServerException(ErrorMsg.API_SERVER_EXCEPTION);
        }
    }
}
