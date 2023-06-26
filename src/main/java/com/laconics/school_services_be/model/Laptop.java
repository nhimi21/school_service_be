package com.laconics.school_services_be.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "laptops")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer laptopId;
    @Column(nullable = false)
    private String brandName;
    private String description;
    @Column(nullable = false)
    private Integer userId;
}
