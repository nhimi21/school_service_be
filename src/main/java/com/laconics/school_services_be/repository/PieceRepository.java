package com.laconics.school_services_be.repository;

import com.laconics.school_services_be.model.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceRepository extends JpaRepository<Piece, Integer> {
}
