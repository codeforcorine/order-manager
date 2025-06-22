package com.technical.evaluation.orders.features.client.mapper;
import com.technical.evaluation.orders.features.client.dto.ClientDto;
import com.technical.evaluation.orders.features.client.dto.CreateClientRequest;
import com.technical.evaluation.orders.features.client.dto.UpdateClientRequest;
import com.technical.evaluation.orders.features.client.entity.Client;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {

    ClientDto convertToDto(Client clientEntity);
    List<ClientDto> clientDtoList(List<Client> clientList);
    Client convertToEntity(CreateClientRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Client client, UpdateClientRequest request);
}
