/*
*
*
*

package com.alugai.alugaai.controller;

import com.alugai.alugaai.domain.User;
import com.alugai.alugaai.service.impl.UserManagementService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario_gerenciamento")
public class UserManagementController {

    @Autowired
    private UserManagementService userManagementService;

    @PostMapping("/senha-codigo")
    public String requestCode(@RequestBody User user) {
        return userManagementService.requestCode(user.getEmail());
    }

    @PostMapping("/senha-alterar")
    public String alterPassword(@RequestBody User user) {

        return userManagementService.alterPassword(user);
    }

}

*
* */
