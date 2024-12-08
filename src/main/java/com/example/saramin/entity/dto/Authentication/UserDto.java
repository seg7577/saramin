package com.example.saramin.entity.dto.Authentication;

import com.example.saramin.entity.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    private String email;
    private String username;
    private String phoneNumber;

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getProfile().getUsername())
                .phoneNumber(user.getProfile().getPhoneNumber())
                .build();
    }
}
