package com.laconics.school_services_be.service;

import com.laconics.school_services_be.auth.UserInfoDetails;
import com.laconics.school_services_be.exception.BusinessException;
import com.laconics.school_services_be.model.Ticket;

import java.util.List;

public interface TicketService {
    Ticket saveTicket(Ticket entity, String username) throws BusinessException;

    List<Ticket> findAllTicket(String username);

    Ticket findTicketById(Integer id) throws BusinessException;

    Ticket updateTicket(Ticket entity, UserInfoDetails user) throws BusinessException;

    void deleteTicketById(Integer id) throws BusinessException;

    List<Ticket> getByStatusAndUser(Integer idUser, String status);

}
