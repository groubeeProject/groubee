package com.gig.groubee.core.model.role;

import com.gig.groubee.core.model.Admin;
import com.gig.groubee.core.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "admin_role_tb")
@Getter @Setter
public class AdminRole extends BaseEntity {

    @Id @GeneratedValue
    private Long adminRoleId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;
}
