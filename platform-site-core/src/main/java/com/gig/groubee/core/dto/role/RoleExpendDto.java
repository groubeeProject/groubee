package com.gig.groubee.core.dto.role;

import com.gig.groubee.core.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : Jake
 * @date : 2021-06-07
 */
@Getter
@Setter
public class RoleExpendDto extends RoleDto {

    private int sortOrder;
    private int level;

    public RoleExpendDto(Role r) {
        super(r);
        this.sortOrder = r.getSortOrder();
        this.level = r.getLevel();
    }
}
