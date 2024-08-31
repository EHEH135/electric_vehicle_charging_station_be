package com.example.electricStation.exception;

public interface ErrorMsg {

    String API_SERVER_EXCEPTION = "전기차 충전소 API를 호출하지 못하였습니다";
    String USER_NOT_FOUND_EXCEPTION = "일치하는 유저가 존재하지 않습니다.";
    String STATION_NOT_FOUND_EXCEPTION = "일치하는 충전소가 존재하지 않습니다.";
    String BOOKMARK_NOT_FOUND_EXCEPTION = "일치하는 즐겨찾기가 존재하지 않습니다.";
}
