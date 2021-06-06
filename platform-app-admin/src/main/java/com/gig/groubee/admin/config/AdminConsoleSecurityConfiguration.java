package com.gig.groubee.admin.config;

import com.gig.groubee.core.security.AbstractSecurityConfiguration;
import com.gig.groubee.core.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : Jake
 * @date : 2021-06-07
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class AdminConsoleSecurityConfiguration extends AbstractSecurityConfiguration {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminService adminService;

    @Autowired
    @Qualifier("adminUserDetailsService")
    private UserDetailsService userDetailsService;
}
