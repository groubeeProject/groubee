package com.gig.groubee.core.dto.menu;

import com.gig.groubee.core.dto.role.RoleDto;
import com.gig.groubee.core.model.Menu;
import com.gig.groubee.core.model.Role;
import com.gig.groubee.core.types.AntMatcherType;
import com.gig.groubee.core.types.MenuType;
import com.gig.groubee.core.types.YNType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
public class MenuDto {

    private Long menuId;

    private String menuName;

    private String url;

    private String iconClass;

    private YNType activeYn;

    private YNType displayYn;

    private int sortOrder;

    private AntMatcherType antMatcherType;

    private MenuType menuType;

    @Builder.Default
    private List<MenuDto> children = new ArrayList<>();

    @Builder.Default
    private List<RoleDto> roles = new ArrayList<>();

    @Builder.Default
    private String activeClass = "";

    private Long parentId;

    @Builder.Default
    private int lv = 0;

    public MenuDto(Menu m) {
        this.menuId = m.getMenuId();
        this.menuName = m.getMenuName();
        this.activeYn = m.getActiveYn();
        this.displayYn = m.getDisplayYn();
        this.sortOrder= m.getSortOrder();
        this.url = m.getUrl();
        this.iconClass = m.getIconClass();
        this.antMatcherType = m.getAntMatcherType();
        this.menuType = m.getMenuType();

        this.roles = m.getRoles().stream().map(RoleDto::new).collect(Collectors.toList());
    }

    public MenuDto(Menu menu, boolean makeChildren) {
        this(menu);
        if (makeChildren && menu.getChildren() != null) {
            for (Menu c : menu.getChildren()) {
                if (c.getDeleteYn() == YNType.N)
                    this.children.add(new MenuDto(c, true));
            }
        }
    }

    /**
     * @param menu
     * @param roles
     * @param checkActive 액티브 유무 확인
     */
    public MenuDto(Menu menu, Collection<Role> roles, boolean checkActive) {
        this(menu);
        if (menu.getChildren() != null) {
            for (Menu c : menu.getChildren()) {
                if (checkActive && c.getActiveYn() == YNType.N) continue;
                if (c.getDisplayYn() == YNType.N) continue;

                long cnt = roles.stream().filter(r -> c.getRoles().contains(r)).count();
                if (cnt > 0) {
                    this.children.add(new MenuDto(c, roles, checkActive));
                }
            }
        }
    }
}
