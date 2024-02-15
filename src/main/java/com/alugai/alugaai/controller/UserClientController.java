package com.alugai.alugaai.controller;

import com.alugai.alugaai.domain.User;
import com.alugai.alugaai.dto.UserRegistrationDto;
import com.alugai.alugaai.service.impl.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cliente")
public class UserClientController {

    @Autowired
    private UserClientService userClientService;

    @PostMapping("/")
    public User insert(@RequestBody UserRegistrationDto userRegistrationDto){
        return userClientService.register(userRegistrationDto);
    }

}
