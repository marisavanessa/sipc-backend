package com.loliana.sipc.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureDTO {
    private Integer id;
    private String name;
    private Integer typeProcedureId;

    public void setName(String name) {
        this.name = name != null ? name.toUpperCase() : null;
    }
}
