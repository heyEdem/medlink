package com.edem.medlink.controller;

import com.edem.medlink.dto.AboutUserResponse;
import com.edem.medlink.dto.GenericResponseMessage;
import com.edem.medlink.dto.UpdateUserProfileRequest;
import com.edem.medlink.service.accountdetails.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Get details of logged in user",
            method = "POST"
    )
    @GetMapping("/about-me")
    public AboutUserResponse aboutMe(Authentication authentication){
        return userService.aboutMe(authentication);
    }

    @Operation(
            summary = "Update user details",
            method = "PATCH"
    )
    @PatchMapping("/profile-settings")
    public GenericResponseMessage updateUser(@RequestBody UpdateUserProfileRequest request, Authentication authentication){
       return userService.updateUserSettings(request, authentication);
    }
}
