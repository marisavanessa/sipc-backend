package com.loliana.sipc.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historical_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricalItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "historical_id", nullable = false)
    private Historical historical;

    @ManyToOne
    @JoinColumn(name = "procedure_id", nullable = false)
    private Procedure procedure;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    private LocalDateTime dateIn;
    private LocalDateTime dateOut;

    private Float amount;
}
