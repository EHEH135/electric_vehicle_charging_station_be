package com.example.electricStation.controller;

import com.example.electricStation.common.CommonResponseService;
import com.example.electricStation.dto.AddUserRequestDto;
import com.example.electricStation.dto.SingleResponse;
import com.example.electricStation.service.RefreshTokenService;
import com.example.electricStation.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final CommonResponseService commonResponseService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/user")
    public String signup(AddUserRequestDto request) {
        userService.save(request);
        return "redirect:/login";
    }

    @GetMapping("/api/v1/logout")
    public SingleResponse<String> logout(HttpServletRequest request, HttpServletResponse response) {
        refreshTokenService.delete();

        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());

        return commonResponseService.getSingleResponse("Logout Success");
    }
}