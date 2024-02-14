package com.alugai.alugaai.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "permission_user")
public class PermissionUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    private Permission permission;

    public void setUser(User user) {
        this.user = user;
    }
    public void setPermission(Permission permission) {
        this.permission = permission;
    }

}