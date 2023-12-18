package com.epam.esm.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDtoShort {
    private LocalDateTime createdTime;
    private Float price;
}
