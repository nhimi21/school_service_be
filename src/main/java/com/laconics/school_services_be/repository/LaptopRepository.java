package com.laconics.school_services_be.repository;

import com.laconics.school_services_be.model.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Integer> {
    List<Laptop> findLaptopsByUserId(Integer userId);

    @Query(value = "select l from Laptop l where l.laptopId=?1 and l.userId=?2")
    Laptop findByIdAndOwner(Integer laptopId, Integer userId);

}
