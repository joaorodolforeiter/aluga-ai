package com.alugai.alugaai.service.impl;

import com.alugai.alugaai.domain.Permission;
import com.alugai.alugaai.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public List<Permission> searchAll(){
        return  permissionRepository.findAll();
    }

    public  Permission insert(Permission permission){
        Permission permissionNew = permissionRepository.saveAndFlush(permission);
        return permissionNew;
    }

    public  Permission alter(Permission permission){
        return  permissionRepository.saveAndFlush(permission);
    }

    public void delete(long id){
        Permission permission = permissionRepository.findById(id).get();
        permissionRepository.delete(permission);
    }

}
