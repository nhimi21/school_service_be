package com.laconics.school_services_be.mapper;

import com.laconics.school_services_be.dto.LaptopDto;
import com.laconics.school_services_be.model.Laptop;
import org.springframework.stereotype.Component;

@Component
public class LaptopMapper implements IMapper<Laptop, LaptopDto>{
    @Override
    public LaptopDto toDto(Laptop laptop) {
        return LaptopDto.builder()
                .brandName(laptop.getBrandName())
                .description(laptop.getDescription())
                .build();
    }

    @Override
    public Laptop toEntity(LaptopDto laptopDto) {
        return Laptop.builder()
                .brandName(laptopDto.getBrandName())
                .description(laptopDto.getDescription())
                .build();
    }
}
