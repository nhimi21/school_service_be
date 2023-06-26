package com.laconics.school_services_be.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PieceDto {

    @NotNull(message = "name is required")
    private String name;

    private String description;

    @NotNull(message = "price is required")
    private Double price;

    @NotNull(message = "stock is required")
    private Integer stock;

}
