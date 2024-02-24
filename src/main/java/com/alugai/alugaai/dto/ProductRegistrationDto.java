package com.alugai.alugaai.dto;

import com.alugai.alugaai.model.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductRegistrationDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    private Integer price;

    @NotEmpty
    private Category category;

    private MultipartFile photo;

}
