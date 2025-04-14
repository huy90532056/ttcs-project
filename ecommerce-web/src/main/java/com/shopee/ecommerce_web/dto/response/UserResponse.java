package com.shopee.ecommerce_web.dto.response;

import com.shopee.ecommerce_web.util.Gender;
import com.shopee.ecommerce_web.util.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String password;
    String firstName;
    String lastName;
    String address;
    LocalDate dob;
    Set<RoleResponse> roles;
}