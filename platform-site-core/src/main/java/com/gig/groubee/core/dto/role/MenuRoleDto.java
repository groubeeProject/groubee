package com.gig.groubee.core.dto.role;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
@Getter @Setter
public class MenuRoleDto {

    private Long menuRoleId;

    private Long menuId;

    private Long roleId;

    public MenuRoleDto(MenuRole m) {
        this.menuRoleId = m.getMenuRoleId();
        if (m.getMenu() != null) this.menuId = m.getMenu().getMenuId();
        if (m.getRole() != null) this.roleId = m.getRole().getRoleId();
    }
}
