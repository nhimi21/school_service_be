package com.laconics.school_services_be.controller;

import com.laconics.school_services_be.auth.UserInfoDetails;
import com.laconics.school_services_be.dto.TicketDto;
import com.laconics.school_services_be.exception.BusinessException;
import com.laconics.school_services_be.mapper.TicketMapper;
import com.laconics.school_services_be.model.Ticket;
import com.laconics.school_services_be.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @PostMapping("/add")
    public ResponseEntity<Ticket> newTicket(@Valid @RequestBody TicketDto ticketDto,
                                            Authentication auth) throws BusinessException {
        UserInfoDetails user = (UserInfoDetails) auth.getPrincipal();
        Ticket ticket = ticketService.saveTicket(ticketMapper.toEntity(ticketDto), user.getUsername());
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Ticket> updateTicket(@Valid @RequestBody Ticket entity,
                                               Authentication auth) throws BusinessException {
        UserInfoDetails user = (UserInfoDetails) auth.getPrincipal();
        Ticket ticket = ticketService.updateTicket(entity, user);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("findAll")
    public List<Ticket> getAllTicket(Authentication auth){
        UserInfoDetails user = (UserInfoDetails) auth.getPrincipal();
        return ticketService.findAllTicket(user.getUsername());
    }

    @GetMapping("/findByTicketId/{ticketId}")
    public Ticket getTicketById(@PathVariable("ticketId") Integer id) throws BusinessException {
        return ticketService.findTicketById(id);
    }

    @DeleteMapping("/delete/{ticketId}")
    public ResponseEntity<String> deleteTicket(@PathVariable("ticketId") Integer id) throws BusinessException {
        ticketService.deleteTicketById(id);
        return ResponseEntity.ok("Ticket deleted successfully");
    }


    @GetMapping("/findByStatusAndIdUser")
    public ResponseEntity<List<Ticket>> findByStatusAndUser(
            @RequestParam(name = "userID", required = false) Integer idUser,
            @RequestParam(name = "status", required = false) String status){
        List<Ticket> tickets = ticketService.getByStatusAndUser(idUser, status);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/changeStatus")
    public ResponseEntity<Ticket> changeStatusOfTicket(
            @RequestParam(name = "idTicket") Integer id,
            @RequestParam(name = "status", required = false) String status) throws BusinessException {
        Ticket ticket = ticketService.setStatusOfTicket(id, status);
        return ResponseEntity.ok(ticket);
    }

}
