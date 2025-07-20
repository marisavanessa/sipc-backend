package com.loliana.sipc.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricalDTO {
    private Integer id;
    private Integer userId;
    private Integer entityId;
    private String scheduled;
    private LocalDateTime dateScheduled;
    private LocalDateTime dateIn;
    private LocalDateTime dateOut;
}

