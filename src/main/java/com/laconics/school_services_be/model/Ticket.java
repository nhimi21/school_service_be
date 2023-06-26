package com.laconics.school_services_be.model;

import com.laconics.school_services_be.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "tickets")
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketId;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(
            name = "id_laptop",
            referencedColumnName = "laptopId",
            nullable = false)
    private Laptop laptop;

    @ManyToOne
    @JoinColumn(
            name = "id_piece",
            referencedColumnName = "pieceId",
            nullable = false)
    private Piece piece;


}
