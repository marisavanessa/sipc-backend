package com.loliana.sipc.model;

import com.loliana.sipc.validation.CPF;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 11, nullable = false, unique = true)
    @NotBlank(message = "CPF é obrigatório")
    @CPF
    private String cpf;

    @Column(length = 20)
    private String cns;

    @Column(length = 40, nullable = false)
    private String name;

    @Column(length = 40)
    private String socialName;

    @Column(length = 8)
    private String cep;

    @Column(length = 40)
    private String address;

    @Column(length = 6)
    private String number;

    @Column(length = 40)
    private String district;

    @Column(length = 40)
    private String city;

    @Column(length = 2)
    private String uf;

    private LocalDateTime birthDate;

    @Column(length = 1)
    private String gender;

    @PrePersist
    @PreUpdate
    public void toUpperCaseFields() {
        if (cpf != null) cpf = cpf.toUpperCase();
        if (cns != null) cns = cns.toUpperCase();
        if (name != null) name = name.toUpperCase();
        if (socialName != null) socialName = socialName.toUpperCase();
        if (cep != null) cep = cep.toUpperCase();
        if (address != null) address = address.toUpperCase();
        if (number != null) number = number.toUpperCase();
        if (district != null) district = district.toUpperCase();
        if (city != null) city = city.toUpperCase();
        if (uf != null) uf = uf.toUpperCase();
        if (gender != null) gender = gender.toUpperCase();
    }

}
