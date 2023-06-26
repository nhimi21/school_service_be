package com.laconics.school_services_be.controller;

import com.laconics.school_services_be.dto.PieceDto;
import com.laconics.school_services_be.exception.BusinessException;
import com.laconics.school_services_be.model.Piece;
import com.laconics.school_services_be.service.PieceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/piece")
@RequiredArgsConstructor
public class PieceController {
    private final PieceService pieceService;

    @PostMapping("/add")
    public ResponseEntity<PieceDto> newLaptopPiece(@Valid @RequestBody PieceDto entity) throws BusinessException {
        PieceDto piece = pieceService.savePiece(entity);
        return new ResponseEntity<>(piece, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Piece> laptopPiece(@Valid @RequestBody Piece entity) throws BusinessException {
        Piece piece = pieceService.updatePiece(entity);
        return ResponseEntity.ok(piece);
    }

    @GetMapping("/findAll")
    public List<Piece> getAllPieces(){
        return pieceService.findAllPieces();
    }

    @GetMapping("/findById/{pieceId}")
    public Piece getPieceById(@PathVariable("pieceId") Integer id) throws BusinessException {
        return pieceService.findPieceById(id);
    }

    @DeleteMapping("/delete/{pieceId}")
    public ResponseEntity<String> deletePiece(@PathVariable("pieceId") Integer id) throws BusinessException {
        pieceService.deleteById(id);
        return ResponseEntity.ok("Piece deleted successfully");
    }

}
