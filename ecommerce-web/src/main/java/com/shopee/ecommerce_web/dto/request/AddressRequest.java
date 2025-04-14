package com.shopee.ecommerce_web.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressRequest {
    String apartmentNumber;
    String floor;
    String building;
    String streetNumber;
    String street;
    String city;
    String country;
    String userId;
}
