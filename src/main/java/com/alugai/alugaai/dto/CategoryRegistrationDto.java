package com.alugai.alugaai.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CategoryRegistrationDto {

    @NotEmpty
    private String name;

    private MultipartFile photo;

}
