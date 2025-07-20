package com.loliana.sipc.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/*
public record EntityDTO(
        @Pattern(regexp = "\\d{14}", message = "CNPJ inválido") String cnpj,
        @NotBlank String name,
        @Pattern(regexp = "\\d{8}", message = "CEP inválido") String cep,
        String address,
        String number,
        String district,
        String city,
        String uf,
        Integer typeEstablishmentId
) {}
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityDTO{
        @Pattern(regexp = "\\d{14}", message = "CNPJ inválido")
        private String cnpj;
        @NotBlank
        private String name;
        @Pattern(regexp = "\\d{8}", message = "CEP inválido")
        private String cep;
        private String address;
        private String number;
        private String district;
        private String city;
        private String uf;
        private Integer typeEstablishmentId;
}
