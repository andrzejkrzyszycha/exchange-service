package com.example.exchange.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    Long user_id;
    String firstName;
    String lastName;
}
