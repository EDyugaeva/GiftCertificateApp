package com.epam.esm.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private float price;
    private int duration;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name="create_date")
    private LocalDateTime createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name="last_update_date")
    private LocalDateTime lastUpdateDate;
    @ManyToMany
    @JoinTable(
            name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "gift_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tagSet;
    public GiftCertificate(String name, String description, float price,
                           int duration, LocalDateTime createDate, LocalDateTime lastUpdateDate, Set<Tag> tagSet) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tagSet = tagSet;
    }
}
