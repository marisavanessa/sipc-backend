package com.loliana.sipc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "entities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 14, nullable = false, unique = true)
    private String cnpj;

    @Column(length = 40, nullable = false)
    private String name;

    @Column(length = 8, nullable = false)
    private String cep;

    @Column(length = 40)
    private String address;

    @Column(length = 6)
    private String number;

    @Column(length = 20)
    private String district;

    @Column(length = 20)
    private String city;

    @Column(length = 2)
    private String uf;

    @Column(name = "type_establishment_id")
    private Integer typeEstablishmentId;
}

