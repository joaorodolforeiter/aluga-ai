package com.alugai.alugaai.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch =  FetchType.EAGER)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    private User renter;

    private LocalDate startDate;
    private LocalDate endDate;

    public Long getNumberOfDays() {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    private boolean approved;

}
