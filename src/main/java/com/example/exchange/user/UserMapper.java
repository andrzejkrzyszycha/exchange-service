package com.example.exchange.user;

public class UserMapper {

    public static UserDto toDto(User user){
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
