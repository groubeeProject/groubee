package com.gig.groubee.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
@NoArgsConstructor
public class InvalidRequiredParameter extends Throwable {
    public static final int ITEM_OUT_OF_STOCK = 2000;

    @Getter
    private int code;

    public InvalidRequiredParameter(String message, int code) {
        super(message);
        this.code = code;
    }

    public InvalidRequiredParameter(String message) {
        super(message);
    }

    public InvalidRequiredParameter(int code) {
        super("InvalidRequiredParameter: " + code);
        this.code = code;
    }
}