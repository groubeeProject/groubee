package com.gig.groubee.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "member_tb", indexes = {
        @Index(columnList = "username", unique = true)
})
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member extends AbstractUser {

    @Id
    @SequenceGenerator(name = "MEMBER_SEQ", sequenceName = "MEMBER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ")
    private Long memberId;

    @Override
    public Long getId() {
        return this.memberId;
    }
}
