package com.gig.groubee.core.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuType {

    AdminConsole("adminConsole", "관리자"),
    Web("web", "웹"),
    App("app", "어플"),
    Api("api", "api");

    final private String type;
    final private String description;
}
