package com.gig.groubee.core.model.role;

import com.gig.groubee.core.model.BaseEntity;
import com.gig.groubee.core.model.Menu;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "menu_role_tb")
@Getter @Setter
public class MenuRole extends BaseEntity {

    @Id @GeneratedValue
    private Long menuRoleId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public MenuRole(Menu m, Role r) {
        this.menu = m;
        this.role = r;
    }
}
