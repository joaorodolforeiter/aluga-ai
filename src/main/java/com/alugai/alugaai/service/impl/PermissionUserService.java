package com.alugai.alugaai.service.impl;

import com.alugai.alugaai.domain.Permission;
import com.alugai.alugaai.domain.PermissionUser;
import com.alugai.alugaai.domain.User;
import com.alugai.alugaai.repository.PermissionRepository;
import com.alugai.alugaai.repository.PermissionUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionUserService {

    @Autowired
    private PermissionUserRepository permissionUserRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public void linkPersonPermissionClient(User user){
        List<Permission> listPermisssion = permissionRepository.findByName("cliente");
        if (listPermisssion.size() > 0){
            PermissionUser permissionUser = new PermissionUser();
            permissionUser.setUser(user);
            permissionUser.setPermission(listPermisssion.get(0));
            permissionUserRepository.saveAndFlush(permissionUser);
        }
    }
}
