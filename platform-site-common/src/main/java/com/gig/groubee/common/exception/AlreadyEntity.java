package com.gig.groubee.common.exception;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
public class AlreadyEntity extends Exception {
    public AlreadyEntity(String entityName) {
        super(entityName);
    }
}
