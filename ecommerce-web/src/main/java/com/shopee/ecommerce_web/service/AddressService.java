package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.dto.request.AddressRequest;
import com.shopee.ecommerce_web.dto.response.AddressResponse;
import com.shopee.ecommerce_web.entity.Address;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.AddressRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressService {
    AddressRepository addressRepository;

    public AddressResponse createAddress(AddressRequest request) {
        Address address = Address.builder()
                .apartmentNumber(request.getApartmentNumber())
                .floor(request.getFloor())
                .building(request.getBuilding())
                .streetNumber(request.getStreetNumber())
                .street(request.getStreet())
                .city(request.getCity())
                .country(request.getCountry())
                .build();

        address = addressRepository.save(address);

        return mapToResponse(address);
    }

    public List<AddressResponse> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public AddressResponse getAddress(String addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        return mapToResponse(address);
    }

    public AddressResponse updateAddress(String addressId, AddressRequest request) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        address.setApartmentNumber(request.getApartmentNumber());
        address.setFloor(request.getFloor());
        address.setBuilding(request.getBuilding());
        address.setStreetNumber(request.getStreetNumber());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());

        address = addressRepository.save(address);

        return mapToResponse(address);
    }

    public void deleteAddress(String addressId) {
        if (!addressRepository.existsById(addressId)) {
            throw new AppException(ErrorCode.ADDRESS_NOT_FOUND);
        }
        addressRepository.deleteById(addressId);
    }

    private AddressResponse mapToResponse(Address address) {
        return AddressResponse.builder()
                .addressId(address.getAddressId())
                .apartmentNumber(address.getApartmentNumber())
                .floor(address.getFloor())
                .building(address.getBuilding())
                .streetNumber(address.getStreetNumber())
                .street(address.getStreet())
                .city(address.getCity())
                .country(address.getCountry())
                .build();
    }
}
