package com.example.velora_ecommerce.entities;

import com.example.velora_ecommerce.dtos.*;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public static CustomerProfileDto toProfileDto(Customer customer) {
        Address customerAddress = customer.getAddress();

        CustomerProfileDto customerProfileDto = new CustomerProfileDto();
        AddressDto addressDto = new AddressDto();

        customerProfileDto.setFirstName(customer.getFirstName());
        customerProfileDto.setLastName(customer.getLastName());
        customerProfileDto.setEmail(customer.getEmail());
        customerProfileDto.setPhoneNumber(customer.getPhoneNumber());

        addressDto.setStreet(customerAddress.getStreet());
        addressDto.setCity(customerAddress.getCity());
        addressDto.setState(customerAddress.getState());
        addressDto.setZipCode(customerAddress.getZipCode());

        customerProfileDto.setAddress(addressDto);

        return customerProfileDto;
    }

    public static CustomerUpdateDto toUpdateDto(Customer customer) {
        Address customerAddress = customer.getAddress();

        CustomerUpdateDto updateDto = new CustomerUpdateDto();
        AddressDto addressDto = new AddressDto();

        updateDto.setFirstName(customer.getFirstName());
        updateDto.setLastName(customer.getLastName());
        updateDto.setEmail(customer.getEmail());
        updateDto.setPhoneNumber(customer.getPhoneNumber());

        addressDto.setStreet(customerAddress.getStreet());
        addressDto.setCity(customerAddress.getCity());
        addressDto.setState(customerAddress.getState());
        addressDto.setZipCode(customerAddress.getZipCode());

        updateDto.setAddress(addressDto);

        return updateDto;
    }

}
