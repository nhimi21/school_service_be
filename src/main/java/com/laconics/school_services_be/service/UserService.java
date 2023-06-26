package com.laconics.school_services_be.service;

import com.laconics.school_services_be.dto.UserRequest;
import com.laconics.school_services_be.exception.BusinessException;
import com.laconics.school_services_be.model.User;

import java.util.List;

public interface UserService {

    User crateUser(UserRequest request) throws BusinessException;
    List<User> getAllUsers();

}
