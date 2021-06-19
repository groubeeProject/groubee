package com.gig.groubee.core.model;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ROLE_PRIVILEGE",
            joinColumns = @JoinColumn(name = "ROLE"),
            inverseJoinColumns = @JoinColumn(name = "PRIVILEGE")
    )
    private Set<Privilege> privileges = new HashSet<>();

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

    @ManyToMany(mappedBy = "roles")
    @Builder.Default
    private Set<Menu> menus = new HashSet<>();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Role) {
            return this.roleName.equals(((Role) obj).getRoleName());
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return this.roleName.hashCode();
    }
}
