package com.epam.esm.mappers;

import com.epam.esm.dto.OrderDtoShort;
import com.epam.esm.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDtoShort orderDtoShort(Order order);
}
