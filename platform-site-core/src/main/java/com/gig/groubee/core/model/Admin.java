package com.gig.groubee.core.model;

import com.gig.groubee.core.types.YNType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "admin_tb", indexes = {
        @Index(columnList = "username", unique = true)
})
@Getter
@AllArgsConstructor @NoArgsConstructor
public class Admin extends AbstractUser {

    @Id
    @SequenceGenerator(name = "ADMIN_SEQ", sequenceName = "ADMIN_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADMIN_SEQ")
    private Long adminId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "admin_role",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "role")
    )
    @OrderBy("level desc, sortOrder asc")
    private Set<Role> roles = new HashSet<>();

    /**
     * 2-Factor 인증 대상자
     * 메시지 모듈 연동 시 default 값 Y 로 바꿀 예정
     */
    @Column(length = 1, columnDefinition = "char(1) default 'N'")
    @Enumerated(EnumType.STRING)
    private YNType multiFactorAuthYn;

    /**
     * 2-Factor 인증 일시
     */
    private LocalDateTime multiFactorAuthAt;

    @Override
    public Long getId() {
        return this.adminId;
    }

    @Override
    public void addRole(Role role) {
        this.roles.add(role);

    }

    @Override
    public void removeRole(Role role) {
        this.roles.remove(role);
    }
}
