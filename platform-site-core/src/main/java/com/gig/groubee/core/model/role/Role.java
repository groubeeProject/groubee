package com.gig.groubee.core.model.role;

import com.gig.groubee.core.model.BaseEntity;
import com.gig.groubee.core.types.YNType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role_tb")
@Getter @Setter
@SuperBuilder
@NoArgsConstructor @AllArgsConstructor
public class Role extends BaseEntity {

    @Id
    @SequenceGenerator(name = "ROLE_SEQ", sequenceName = "ROLE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQ")
    private Long roleId;

    @OneToMany(mappedBy = "privilege", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<RolePrivilege> privileges = new HashSet<>();

    @Column(length = 20, unique = true)
    private String roleName;

    private String description;

    private int sortOrder;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    @Builder.Default
    private YNType deleteYn = YNType.N;

    /**
     * Role의 레벨
     * 작을 수록 높은 레벨(0 -> 99)
     */
    @Column
    private int level = 99;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<MenuRole> menuRoles = new HashSet<>();

}
