package com.laconics.school_services_be.mapper;

import com.laconics.school_services_be.dto.TicketDto;
import com.laconics.school_services_be.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper implements IMapper<Ticket, TicketDto> {
    @Override
    public TicketDto toDto(Ticket ticket) {
        return TicketDto.builder()
                .description(ticket.getDescription())
                .laptop(ticket.getLaptop())
                .piece(ticket.getPiece())
                .build();
    }

    @Override
    public Ticket toEntity(TicketDto ticketDto) {
        return Ticket.builder()
                .piece(ticketDto.getPiece())
                .laptop(ticketDto.getLaptop())
                .description(ticketDto.getDescription())
                .build();
    }
}
