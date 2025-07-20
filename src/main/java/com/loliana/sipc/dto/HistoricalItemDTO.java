package com.loliana.sipc.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricalItemDTO {

    private Integer id;
    private Integer historicalId;
    private Integer procedureId;
    private Integer doctorId;
    private LocalDateTime dateIn;
    private LocalDateTime dateOut;
    private Float amount;
}
