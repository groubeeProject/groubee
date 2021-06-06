package com.gig.groubee.core.dto.role;

import com.gig.groubee.core.model.role.Role;
import com.gig.groubee.core.types.YNType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
@Getter @Setter
public class RoleDto {

    private Long roleId;

    private String roleName;

    private String description;

    private int sortOrder;

    private YNType deleteYn;

    private int level;

    public RoleDto(Role r) {
        this.roleId = r.getRoleId();
        this.roleName = r.getRoleName();
        this.description = r.getDescription();
        this.sortOrder = r.getSortOrder();
        this.deleteYn = r.getDeleteYn();
        this.level = r.getLevel();
    }
}
