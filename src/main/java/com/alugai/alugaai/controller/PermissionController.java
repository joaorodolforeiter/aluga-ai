package com.alugai.alugaai.controller;

import com.alugai.alugaai.domain.Permission;
import com.alugai.alugaai.service.impl.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/permissao")
public class PermissionController {



    @Autowired
    private PermissionService permissionService;

    @GetMapping("/")
    public List<Permission> searchAll(){
        return  permissionService.searchAll();
    }

    @PostMapping("/")
    public  Permission insert(@RequestBody Permission permission){
        return  permissionService.insert(permission);
    }

    @PutMapping("/")
    public  Permission alter(@RequestBody Permission permission){
        return  permissionService.alter(permission);
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> delete(@PathVariable("id") long id){
        permissionService.delete(id);
        return ResponseEntity.ok().build();
    }



}
