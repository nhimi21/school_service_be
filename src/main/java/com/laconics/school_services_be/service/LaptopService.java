package com.laconics.school_services_be.service;

import com.laconics.school_services_be.exception.BusinessException;
import com.laconics.school_services_be.model.Laptop;

import java.util.List;

public interface LaptopService {
    Laptop createLaptop(Laptop entity, String username);

    List<Laptop> getAllLaptops(String username);

    Laptop updateLaptop(Laptop entity, String username) throws BusinessException;

    Laptop getLaptopById(Integer id) throws BusinessException;

    void deleteLaptopById(Integer id) throws BusinessException;

}
