package com.laconics.school_services_be.dto;


import com.laconics.school_services_be.model.Laptop;
import com.laconics.school_services_be.model.Piece;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDto {

    @NotNull(message = "laptop is required")
    private Laptop laptop;

    @NotNull(message = "description is required")
    private String description;

    @NotNull(message = "piece is required")
    private Piece piece;

}
