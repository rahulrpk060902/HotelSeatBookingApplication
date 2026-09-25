package com.hotelbooking.project.service;

import com.hotelbooking.project.dto.ApiResponse;
import com.hotelbooking.project.dto.UserProfileResponse;
import com.hotelbooking.project.dto.UserProfileUpdateRequest;
import com.hotelbooking.project.entity.User;
import com.hotelbooking.project.exception.BusinessException;
import com.hotelbooking.project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserProfileServiceImpl implements UserProfileService{

    private final UserRepository userRepository;


    public UserProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public ApiResponse<UserProfileResponse> getUserProfile(UUID userID) {

        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileResponse profile = new UserProfileResponse(
                user.getId(),
                user.getName(),
                user.getPhone(),
                user.getEmail(),
                user.getPlace()
        );

        return new ApiResponse<>(
                "User profile fetched successfully",
                profile
        );
    }



    @Override
    public ApiResponse<UserProfileResponse> updateUserProfile(
            UUID userID,
            UserProfileUpdateRequest request
    ) {

        User user = userRepository.findById(userID)
                .orElseThrow(() -> new BusinessException("User not found"));

        // update only allowed fields
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setPlace(request.getPlace());

        userRepository.save(user);

        return new ApiResponse<>(
                "User profile updated successfully",
                mapToResponse(user)
        );
    }

    private UserProfileResponse mapToResponse(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getName(),
                user.getPhone(),
                user.getEmail(),
                user.getPlace()
        );
    }

}
