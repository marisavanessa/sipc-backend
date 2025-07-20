package com.loliana.sipc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "type_procedures")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeProcedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40, nullable = false)
    private String name;
}