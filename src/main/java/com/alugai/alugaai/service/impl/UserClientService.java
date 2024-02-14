package com.alugai.alugaai.service.impl;

import com.alugai.alugaai.domain.User;
import com.alugai.alugaai.dto.UserRegistrationDto;
import com.alugai.alugaai.repository.UserClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserClientService {

    @Autowired
    private UserClientRepository userClientRepository;

    @Autowired
    private PermissionUserService permissionUserService;

    @Autowired
    private EmailService emailService;

    public User register(UserRegistrationDto userRegistrationDto){
        User user = new UserRegistrationDto().convert(userRegistrationDto);
        User newObject = userClientRepository.saveAndFlush(user);
        permissionUserService.linkPersonPermissionClient(newObject);
        emailService.sendEmailText(newObject.getEmail(),"Cadastro no AlugaAi", " Registro realizado com sucesso. Teste01 ");
        return newObject;

    }

}
