package com.alugai.alugaai.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RentRegistrationDto {

    private Long productId;

    private LocalDate startDate;
    private LocalDate endDate;

}
