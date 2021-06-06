package com.gig.groubee.core.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum YNType {

    Y("y", "활성"),
    N("n", "비활성");

    final private String type;
    final private String description;
}