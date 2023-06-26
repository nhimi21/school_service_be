package com.laconics.school_services_be.controller;

import com.laconics.school_services_be.auth.UserInfoDetails;
import com.laconics.school_services_be.dto.LaptopDto;
import com.laconics.school_services_be.exception.BusinessException;
import com.laconics.school_services_be.mapper.LaptopMapper;
import com.laconics.school_services_be.model.Laptop;
import com.laconics.school_services_be.service.LaptopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/laptop")
@RequiredArgsConstructor
public class LaptopController {
    private final LaptopService laptopService;
    private final LaptopMapper laptopMapper;

    @PostMapping("/add")
    public ResponseEntity<Laptop> newLaptop(@Valid @RequestBody LaptopDto laptopDto, Authentication auth){
        UserInfoDetails user = (UserInfoDetails) auth.getPrincipal();
        Laptop entity = laptopService.createLaptop(laptopMapper.toEntity(laptopDto), user.getUsername());
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Laptop> updateLaptop(@Valid @RequestBody Laptop entity,
                                               Authentication auth) throws BusinessException {
        UserInfoDetails user = (UserInfoDetails) auth.getPrincipal();
        Laptop laptop = laptopService.updateLaptop(entity, user.getUsername());
        return ResponseEntity.ok(laptop);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Laptop>> allLaptops(Authentication auth){
        UserInfoDetails user = (UserInfoDetails) auth.getPrincipal();
        List<Laptop> laptops = laptopService.getAllLaptops(user.getUsername());
        return ResponseEntity.ok(laptops);
    }

    @GetMapping("/{laptopId}")
    public Laptop findLaptopById(@PathVariable("laptopId") Integer id) throws BusinessException {
        return laptopService.getLaptopById(id);
    }

    @DeleteMapping("/delete/{laptopId}")
    public ResponseEntity<String> deleteLaptop(@PathVariable("laptopId") Integer id) throws BusinessException {
        laptopService.deleteLaptopById(id);
        return ResponseEntity.ok("Ticket deleted successfully");
    }

}
