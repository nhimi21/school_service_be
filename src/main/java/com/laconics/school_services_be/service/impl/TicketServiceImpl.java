package com.laconics.school_services_be.service.impl;

import com.laconics.school_services_be.auth.UserInfoDetails;
import com.laconics.school_services_be.enums.Roles;
import com.laconics.school_services_be.enums.Status;
import com.laconics.school_services_be.exception.BusinessException;
import com.laconics.school_services_be.model.Laptop;
import com.laconics.school_services_be.model.Piece;
import com.laconics.school_services_be.model.Ticket;
import com.laconics.school_services_be.model.User;
import com.laconics.school_services_be.repository.LaptopRepository;
import com.laconics.school_services_be.repository.PieceRepository;
import com.laconics.school_services_be.repository.TicketRepository;
import com.laconics.school_services_be.repository.UserRepository;
import com.laconics.school_services_be.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final PieceRepository pieceRepository;
    private final LaptopRepository laptopRepository;
    private final UserRepository userRepository;

    @Override
    public Ticket saveTicket(Ticket entity, String username) throws BusinessException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            entity.setStatus(Status.OPEN);
            Laptop laptop = laptopRepository
                    .findByIdAndOwner(entity.getLaptop().getLaptopId(), user.get().getUserId());
            if (laptop == null){
                throw new BusinessException("The laptop doesn't exist or you aren't owner");
            } else
                entity.setLaptop(laptop);

            Piece piece = pieceRepository.findById(entity.getPiece().getPieceId())
                    .orElseThrow(() -> new BusinessException("This piece doesn't exist!"));
            entity.setPiece(piece);

            if (piece.getStock() > 0){
                Integer stock = piece.getStock() - 1;
                piece.setStock(stock);
                pieceRepository.save(piece);
            } else {
                throw new BusinessException("This piece doesn't have any stock!");
            }
        }

        return ticketRepository.save(entity);
    }

    @Override
    public List<Ticket> findAllTicket(String username) {
        List<Ticket> result = new ArrayList<>();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            String role = user.get().getRole().toString();
            if (role.equals(Roles.ROLE_ADMIN.toString())){
                result = ticketRepository.findAll();
            } else {
                result = ticketRepository.findTicketsByLaptopOwner(user.get().getUserId());
            }
        }

        return result;
    }

    @Override
    public Ticket findTicketById(Integer id) throws BusinessException {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        if (ticket == null)
            throw new BusinessException("Ticket with id " + id +" doesn't exist!");
        return ticket;
    }

    @Override
    public Ticket updateTicket(Ticket entity, UserInfoDetails user) throws BusinessException {
        Ticket ticket = ticketRepository.findById(entity.getTicketId()).orElse(null);
        if (ticket != null){
            Piece actualPiece = ticket.getPiece();
            Integer actualStock = actualPiece.getStock();
            if (ticket.getStatus().equals(Status.CLOSED)){
                throw new BusinessException("The status of ticket is CLOSED!");
            } else {
                Piece piece = pieceRepository.findById(entity.getPiece().getPieceId()).orElse(null);
                if (piece == null){
                    throw new BusinessException("This piece doesn't exist!");
                } else {
                    if (piece.getStock() <= 0){
                        throw new BusinessException("This piece doesn't have any stock!");
                    } else {
                        if (actualPiece != piece){
                            actualPiece.setStock(actualStock + 1);
                            Integer stock = piece.getStock() - 1;
                            piece.setStock(stock);
                            pieceRepository.save(actualPiece);
                            pieceRepository.save(piece);
                        }
                        ticket.setPiece(piece);
                    }
                }

                List<String> authorityNames = user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList();

                for (String role: authorityNames){
                    if (role.equals(Roles.ROLE_ADMIN.toString())){
                        ticket.setStatus(entity.getStatus());
                    }
                }
            }
            ticket.setDescription(entity.getDescription());
            Laptop laptop = laptopRepository.findById(entity.getLaptop().getLaptopId())
                    .orElseThrow(() -> new BusinessException("This laptop doesn't exist!"));
            ticket.setLaptop(laptop);
            ticket = ticketRepository.save(ticket);
        } else
            throw new BusinessException("This ticket doesn't exist!");

        return ticket;
    }

    @Override
    public void deleteTicketById(Integer id) throws BusinessException {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        if (ticket == null){
            throw new BusinessException("Ticket with id " + id +" doesn't exist!");
        } else {
            if (ticket.getStatus().equals(Status.OPEN))
                throw new BusinessException("You cannot delete the ticket with status OPEN!");
            else
                ticketRepository.save(ticket);
        }
    }

    @Override
    public List<Ticket> getByStatusAndUser(Integer idUser, String status) {
        String s = null;
        if (status != null)
            s = status.toUpperCase();
        return ticketRepository.findTicketBYStatusAndUser(s, idUser);
    }

    @Override
    public Ticket setStatusOfTicket(Integer id, String status) throws BusinessException {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Ticket with id " + id +" doesn't exist!"));
        if (status != null){
            String st = status.toUpperCase();
            ticket.setStatus(Status.valueOf(st));
            ticket = ticketRepository.save(ticket);
        }

        return ticket;
    }

}
