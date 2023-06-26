package com.laconics.school_services_be.service.impl;

import com.laconics.school_services_be.dto.UserRequest;
import com.laconics.school_services_be.enums.Roles;
import com.laconics.school_services_be.exception.BusinessException;
import com.laconics.school_services_be.model.User;
import com.laconics.school_services_be.repository.UserRepository;
import com.laconics.school_services_be.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User crateUser(UserRequest request) throws BusinessException {
        Optional<User> existUser = userRepository.findByUsername(request.getUsername());
        User user = new User();
        if (existUser.isEmpty()){
            user.setFullName(request.getFullName());
            user.setUsername(request.getUsername());
            user.setRole(Roles.ROLE_USER);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
        } else
            throw new BusinessException("The username is used!");

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findUsersByRole(Roles.ROLE_USER);
    }

    @PostConstruct
    public void init(){
        Optional<User> user = userRepository.findByUsername("admin");
        if (user.isEmpty()){
            User userAdmin = new User();
            userAdmin.setUsername("admin");
            userAdmin.setPassword(passwordEncoder.encode("admin"));
            userAdmin.setFullName("Administrator");
            userAdmin.setRole(Roles.ROLE_ADMIN);
            userRepository.save(userAdmin);
        }
    }

}

