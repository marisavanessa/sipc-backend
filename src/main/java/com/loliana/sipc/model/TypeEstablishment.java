package com.loliana.sipc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "type_establishments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeEstablishment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40, nullable = false, unique = true)
    private String name;
}