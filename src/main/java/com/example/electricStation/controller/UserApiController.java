package com.example.electricStation.controller;

import com.example.electricStation.common.CommonResponseService;
import com.example.electricStation.dto.AddUserRequestDto;
import com.example.electricStation.dto.SingleResponse;
import com.example.electricStation.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;
    private final CommonResponseService commonResponseService;

    @PostMapping("/user")
    public String signup(AddUserRequestDto request) {
        userService.save(request);
        return "redirect:/login";
    }

    @GetMapping("/api/v1/logout")
    public SingleResponse<String> logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());

        return commonResponseService.getSingleResponse("Logout Success");
    }
}