package com.gig.groubee.common.exception;

import lombok.Getter;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
public class NotFoundException extends Exception {
    public static final int USER_NOT_FOUND = 100;
    public static final int ADMIN_NOT_FOUND = 120;
    public static final int USER_GROUP_NOT_FOUND = 200;
    public static final int ROLE_NOT_FOUND = 300;
    public static final int PRIVILEGE_NOT_FOUND = 301;
    public static final int BOARD_NOT_FOUND = 400;
    public static final int MENU_NOT_FOUND = 500;
    public static final int PARENT_MENU_NOT_FOUND = 501;
    public static final int CODE_NOT_FOUND = 600;
    public static final int PARENT_CODE_NOT_FOUND = 601;

    public static final int MESSAGE_NOT_FOUND = 1700;

    @Getter
    private final int code;


    public NotFoundException(String message, int code) {
        super(message);
        this.code = code;
    }

    public NotFoundException(int code) {
        super("Not Found Exception: " + code);
        this.code = code;
    }
}