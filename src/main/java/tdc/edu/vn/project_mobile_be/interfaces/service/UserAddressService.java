package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.mapstruct.Mapper;
import tdc.edu.vn.project_mobile_be.dtos.requests.CreateAddressRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.user.UserAddress;

@Mapper(componentModel = "spring")
public interface UserAddressService {
    UserAddress created(CreateAddressRequestDTO request);
}