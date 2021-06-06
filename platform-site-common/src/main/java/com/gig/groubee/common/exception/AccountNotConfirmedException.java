package com.gig.groubee.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author : Jake
 * @date : 2021-06-07
 */
public class AccountNotConfirmedException  extends AuthenticationException {
    public AccountNotConfirmedException(String message) {
        super(message);
    }
}
