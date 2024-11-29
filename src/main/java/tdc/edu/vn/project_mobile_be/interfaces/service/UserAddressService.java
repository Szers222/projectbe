package tdc.edu.vn.project_mobile_be.interfaces.service;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import tdc.edu.vn.project_mobile_be.dtos.requests.user.CreateAddressRequestDTO;
import tdc.edu.vn.project_mobile_be.dtos.requests.user.UpdateAddressRequestDTO;
import tdc.edu.vn.project_mobile_be.entities.user.UserAddress;

@Mapper(componentModel = "spring")
public interface UserAddressService {
    UserAddress created(CreateAddressRequestDTO request);
    UserAddress update(@MappingTarget UserAddress userAddress, UpdateAddressRequestDTO request);
}
