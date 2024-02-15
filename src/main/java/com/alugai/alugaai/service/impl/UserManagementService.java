/*
*
*
*
package com.alugai.alugaai.service.impl;


import com.alugai.alugaai.domain.User;
import com.alugai.alugaai.repository.UserRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.text.*;
import java.util.*;

@Service
public class UserManagementService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public String requestCode(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setCodePasswordRecovery(getCodePasswordRecovery(user.getId()));
            userRepository.saveAndFlush(user);
            emailService.sendEmailText(user.getEmail(),"codigo de recuperacao de senha ",
                    "o seu codigo de recuperacao de senha e o seguinte: " + user.getCodePasswordRecovery());
            return "codigo enviado!";
        }else{
            return "Nenhuma pessoa encontrada com o email fornecido";
        }
    }

    public String alterPassword(User user){
        User userBank = userRepository.findByEmailAndCodePasswordRecovery(user.getEmail(), user.getCodePasswordRecovery());
        if (userBank != null){
            DateFormat format = new SimpleDateFormat("ddMMyyyyHHmmssmm");
            try {
                Date codePasswordRecoveryDate = format.parse(userBank.getCodePasswordRecovery().substring(0, 14));
                Date different = new Date(new Date().getTime() - codePasswordRecoveryDate.getTime());
                if (different.getTime()/1000<900) {
                    userBank.setPassword(user.getPassword());
                    userBank.setCodePasswordRecovery(null);
                    userRepository.saveAndFlush(userBank);
                    return "senha alterado com sucesso";
                }else {
                    return "tempo expirado, solicite um novo codigo.";
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return "Erro ao analisar a data";
            }
        }else{
            return "email ou codigo nao encontrado";
        }
    }
    private  String getCodePasswordRecovery(Long id){
        DateFormat format = new SimpleDateFormat("ddMMyyyyHHmmssmm");
        return format.format(new Date()) + id;
    }

}


*
* */
