package com.hotelbooking.project.controller;


import com.hotelbooking.project.dto.ApiResponse;
import com.hotelbooking.project.dto.UserProfileResponse;
import com.hotelbooking.project.dto.UserProfileUpdateRequest;
import com.hotelbooking.project.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/{userID}")
    public ApiResponse<UserProfileResponse> getUserProfile(
            @PathVariable UUID userID) {

        return userProfileService.getUserProfile(userID);
    }

    @PutMapping("/{userID}")
    public ApiResponse<UserProfileResponse> updateUserProfile(
            @PathVariable UUID userID,
            @RequestBody UserProfileUpdateRequest request) {

        return userProfileService.updateUserProfile(userID, request);
    }

}

