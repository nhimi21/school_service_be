package com.laconics.school_services_be.service;

import com.laconics.school_services_be.dto.PieceDto;
import com.laconics.school_services_be.exception.BusinessException;
import com.laconics.school_services_be.model.Piece;

import java.util.List;

public interface PieceService {
    PieceDto savePiece(PieceDto piece) throws BusinessException;

    Piece updatePiece(Piece entity) throws BusinessException;

    List<Piece> findAllPieces();

    void deleteById(Integer id) throws BusinessException;

    Piece findPieceById(Integer id) throws BusinessException;

}
