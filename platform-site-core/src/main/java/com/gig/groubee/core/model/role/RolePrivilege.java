package com.gig.groubee.core.model.role;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "role_privilege_tb")
@Getter @Setter
public class RolePrivilege {

    @Id @GeneratedValue
    private Long rolePrivilegeId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "privilege_id")
    private Privilege privilege;
}
