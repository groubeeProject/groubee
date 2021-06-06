package com.gig.groubee.core.model.role;

import com.gig.groubee.core.model.BaseEntity;
import com.gig.groubee.core.model.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "member_role_tb")
@Getter
@Setter
public class MemberRole extends BaseEntity {

    @Id
    @GeneratedValue
    private Long memberRoleId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
