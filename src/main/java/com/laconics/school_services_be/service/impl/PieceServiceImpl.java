package com.laconics.school_services_be.service.impl;

import com.laconics.school_services_be.dto.PieceDto;
import com.laconics.school_services_be.exception.BusinessException;
import com.laconics.school_services_be.mapper.PieceMapper;
import com.laconics.school_services_be.model.Piece;
import com.laconics.school_services_be.repository.PieceRepository;
import com.laconics.school_services_be.service.PieceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PieceServiceImpl implements PieceService {
    private final PieceRepository pieceRepository;
    private final PieceMapper pieceMapper;

    @Override
    public PieceDto savePiece(PieceDto pieceDto) throws BusinessException {
        if (pieceDto.getStock() == 0){
            throw new BusinessException("You cannot crate piece with stock 0!");
        }
        Piece piece = pieceMapper.toEntity(pieceDto);
        piece = pieceRepository.save(piece);

        return pieceMapper.toDto(piece);
    }

    @Override
    public Piece updatePiece(Piece entity) throws BusinessException {
        Piece piece = pieceRepository.findById(entity.getPieceId()).orElse(null);
        if (piece != null){
            piece.setName(entity.getName());
            piece.setDescription(entity.getDescription());
            piece.setStock(entity.getStock());
            piece.setPrice(entity.getPrice());
            pieceRepository.save(piece);
        } else
            throw new BusinessException("No piece with this id: " + entity.getPieceId());

        return piece;
    }

    @Override
    public List<Piece> findAllPieces() {
        return pieceRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) throws BusinessException {
        Piece piece = pieceRepository.findById(id).orElse(null);
        if (piece == null){
            throw new BusinessException("The piece doesn't exist!");
        } else {
            pieceRepository.delete(piece);
        }
    }

    @Override
    public Piece findPieceById(Integer id) throws BusinessException {
        return pieceRepository.findById(id).orElseThrow(
                () -> new BusinessException("The piece with id: " + id +" doesn't exist!"));
    }

}

