package com.laconics.school_services_be.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LaptopDto {

    @NotNull(message = "brandName is required")
    private String brandName;

    private String description;

}
