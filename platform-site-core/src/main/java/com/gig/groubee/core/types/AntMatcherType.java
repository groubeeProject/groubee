package com.gig.groubee.core.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AntMatcherType {

    Single("single", "설정된 값만 사용(/)"),
    All("all", "하위값까지 포함(/**)");

    final private String type;
    final private String description;
}
