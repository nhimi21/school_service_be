package com.laconics.school_services_be.repository;

import com.laconics.school_services_be.enums.Roles;
import com.laconics.school_services_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    @Query(value = "select u from User u where u.role=?1")
    List<User> findUsersByRole(Roles role);

}
