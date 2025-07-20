package com.loliana.sipc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40, nullable = false)
    private String name;

    @Column(name = "type_council", length = 5, nullable = false)
    private String typeCouncil;

    @Column(name = "id_council", length = 15, nullable = false)
    private String idCouncil;
}
