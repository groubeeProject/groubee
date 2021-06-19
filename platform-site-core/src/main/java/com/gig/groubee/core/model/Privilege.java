package com.gig.groubee.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "privilege_tb")
public class Privilege {

    @Id
    @SequenceGenerator(name = "PRIVILEGE_SEQ", sequenceName = "PRIVILEGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRIVILEGE_SEQ")
    private Long privilegeId;

    private String privilegeName;

    private String description;
}
