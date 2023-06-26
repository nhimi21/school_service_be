package com.laconics.school_services_be.mapper;

import com.laconics.school_services_be.dto.PieceDto;
import com.laconics.school_services_be.model.Piece;
import org.springframework.stereotype.Component;

@Component
public class PieceMapper implements IMapper<Piece, PieceDto>{

    @Override
    public PieceDto toDto(Piece piece) {
        return PieceDto.builder()
                .name(piece.getName())
                .description(piece.getDescription())
                .stock(piece.getStock())
                .price(piece.getPrice())
                .build();
    }

    @Override
    public Piece toEntity(PieceDto pieceDto) {
        return Piece.builder()
                .name(pieceDto.getName())
                .description(pieceDto.getDescription())
                .price(pieceDto.getPrice())
                .stock(pieceDto.getStock())
                .build();
    }

}
