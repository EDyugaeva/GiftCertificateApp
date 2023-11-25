package com.epam.esm.model;

import com.epam.esm.datadecerializer.CustomDateDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.format.DateTimeFormatter.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate {
    private Long id;
    private String name;
    private String description;
    private float price;
    private int duration;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime lastUpdateDate;
    private List<Tag> tagList;

    public GiftCertificate(String name, String description, float price, int duration, LocalDateTime createDate, LocalDateTime lastUpdateDate, List<Tag> tagList) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tagList = tagList;
    }
}
