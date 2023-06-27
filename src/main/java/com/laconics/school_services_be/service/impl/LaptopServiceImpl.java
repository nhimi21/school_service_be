package com.laconics.school_services_be.service.impl;

import com.laconics.school_services_be.enums.Roles;
import com.laconics.school_services_be.exception.BusinessException;
import com.laconics.school_services_be.model.Laptop;
import com.laconics.school_services_be.model.User;
import com.laconics.school_services_be.repository.LaptopRepository;
import com.laconics.school_services_be.repository.UserRepository;
import com.laconics.school_services_be.service.LaptopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LaptopServiceImpl implements LaptopService {
    private final LaptopRepository laptopRepository;
    private final UserRepository userRepository;

    @Override
    public Laptop createLaptop(Laptop entity, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresent(u -> entity.setUserId(u.getUserId()));
        return laptopRepository.save(entity);
    }

    @Override
    public Laptop updateLaptop(Laptop entity, String username) throws BusinessException {
        Optional<User> user = userRepository.findByUsername(username);
        Laptop laptop = new Laptop();
        if (user.isPresent()){
            laptop = laptopRepository.findByIdAndOwner(entity.getLaptopId(), user.get().getUserId());
            if (laptop != null){
                laptop.setDescription(entity.getDescription());
                laptop.setBrandName(entity.getBrandName());
                laptopRepository.save(laptop);
            } else {
                throw new BusinessException("Laptop with id " + entity.getLaptopId() +" doesn't exist" );
            }
        }
        return laptop;
    }

    @Override
    public Laptop getLaptopById(Integer id) throws BusinessException {
        return laptopRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Laptop with id " + id +" doesn't exist!"));
    }

    @Override
    public void deleteLaptopById(Integer id, String username) throws BusinessException {
        Optional<User> user = userRepository.findByUsername(username);
        Laptop laptop;
        if (user.isPresent()){
            Roles role = user.get().getRole();
            if (role.equals(Roles.ROLE_ADMIN)){
                laptop = laptopRepository.findById(id).orElse(null);
            } else {
                laptop = laptopRepository.findByIdAndOwner(id, user.get().getUserId());
            }
            if (laptop == null){
                throw new BusinessException("Laptop with id " + id +" doesn't exist!");
            } else
                laptopRepository.delete(laptop);
        }
    }

    @Override
    public List<Laptop> getAllLaptops(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        List<Laptop> laptops = new ArrayList<>();
        if (user.isPresent()){
            Roles role = user.get().getRole();
            if (role.equals(Roles.ROLE_ADMIN)){
                laptops = laptopRepository.findAll();
            } else {
                laptops = laptopRepository.findLaptopsByUserId(user.get().getUserId());
            }
        }
        return laptops;
    }

}
