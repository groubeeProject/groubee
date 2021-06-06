package com.gig.groubee.core.model;

import com.gig.groubee.core.model.role.MenuRole;
import com.gig.groubee.core.types.AntMatcherType;
import com.gig.groubee.core.types.MenuType;
import com.gig.groubee.core.types.YNType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "menu_tb")
@Getter @Setter
@SuperBuilder
@NoArgsConstructor @AllArgsConstructor
public class Menu extends BaseEntity {

    @Id
    @SequenceGenerator(name = "MENU_SEQ", sequenceName = "MENU_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENU_SEQ")
    private Long menuId;

    private String menuName;

    private String url;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    @Builder.Default
    private YNType deleteYn = YNType.N;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    @Builder.Default
    private YNType displayYn = YNType.N;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    @Builder.Default
    private YNType activeYn = YNType.N;

    private int sortOrder;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AntMatcherType antMatcherType = AntMatcherType.Single;

    private String iconClass;

    @Enumerated(EnumType.STRING)
    private MenuType menuType;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Menu parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayYn desc, sortOrder desc, antMatcherType")
    @Builder.Default
    private List<Menu> children = new ArrayList<>();

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<MenuRole> menuRoles = new HashSet<>();


    /**
     * 하위 메뉴 추가
     *
     * @param menu 하위 메뉴
     */
    public void addChildren(Menu menu) {
        this.children.add(menu);
        //하위 메뉴는 상위 메뉴의 타입을 따라간다.
        menu.setMenuType(this.menuType);
        menu.setParent(this);
    }
}
