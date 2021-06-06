package com.gig.groubee.core.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ModifyType {

    Register("register", "등록"),
    Modify("modify", "수정"),
    Remove("remove", "삭제"),
    Clear("clear", "초기화");

    final private String type;
    final private String description;
}
