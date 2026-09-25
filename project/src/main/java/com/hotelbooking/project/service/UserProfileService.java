package com.hotelbooking.project.service;

import com.hotelbooking.project.dto.ApiResponse;
import com.hotelbooking.project.dto.UserProfileResponse;
import com.hotelbooking.project.dto.UserProfileUpdateRequest;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public interface UserProfileService {
    ApiResponse<UserProfileResponse> getUserProfile(UUID userID);

    ApiResponse<UserProfileResponse> updateUserProfile(UUID userID, UserProfileUpdateRequest request);
}
