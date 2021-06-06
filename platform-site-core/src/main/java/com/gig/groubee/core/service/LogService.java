package com.gig.groubee.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LogService {

    private final SecurityService securityService;

}
