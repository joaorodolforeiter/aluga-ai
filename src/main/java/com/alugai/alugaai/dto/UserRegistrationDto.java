package com.alugai.alugaai.dto;

import com.alugai.alugaai.domain.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class UserRegistrationDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;

    public User convert(UserRegistrationDto userRegistrationDto){
        User user = new User();
        BeanUtils.copyProperties(userRegistrationDto, user);
        return  user;
    }

}
