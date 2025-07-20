package com.loliana.sipc.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historical")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Historical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "scheduled", length = 1)
    private String scheduled;

    @Column(name = "date_scheduled")
    private LocalDateTime dateScheduled;

    @Column(name = "date_in")
    private LocalDateTime dateIn;

    @Column(name = "date_out")
    private LocalDateTime dateOut;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "entity_id", nullable = false)
    private Integer entityId;

    public void toUpperCaseFields() {
        if (scheduled != null) scheduled = scheduled.toUpperCase();
    }




}
