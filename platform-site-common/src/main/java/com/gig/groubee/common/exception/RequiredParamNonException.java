package com.gig.groubee.common.exception;

import lombok.NoArgsConstructor;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
@NoArgsConstructor
public class RequiredParamNonException extends Exception {
    public RequiredParamNonException(String message) {
        super(message);
    }
}
