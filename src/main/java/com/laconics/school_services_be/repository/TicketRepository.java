package com.laconics.school_services_be.repository;

import com.laconics.school_services_be.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query(value = "select t from Ticket t left join t.laptop l where l.userId =?1")
    List<Ticket> findTicketsByLaptopOwner(Integer userId);

    @Query(value = "SELECT t.* FROM tickets t LEFT JOIN laptops l ON l.laptop_id = t.id_laptop \n" +
            "WHERE t.status = IFNULL(:status, t.status) AND l.user_id = IFNULL(:userID, l.user_id)", nativeQuery = true)
    List<Ticket> findTicketBYStatusAndUser(@Param("status") String status, @Param("userID") Integer userId);

}
