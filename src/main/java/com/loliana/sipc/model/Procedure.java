package com.loliana.sipc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "procedures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40, nullable = false)
    private String name;

    @Column(name = "type_procedure_id", nullable = false)
    private Integer typeProcedureId;
}
