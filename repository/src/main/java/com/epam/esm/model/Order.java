package com.epam.esm.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime createdTime;
    private Float price;
//    @OneToOne
//    private GiftCertificate giftCertificate;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
