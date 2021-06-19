package com.gig.groubee.admin.config;

import com.gig.groubee.admin.security.AdminAuthenticationProvider;
import com.gig.groubee.admin.security.AuthenticationFailureHandlerImpl;
import com.gig.groubee.admin.security.AuthenticationSuccessHandlerImpl;
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
    PasswordEncoder passwordEncoder;

    @Autowired
    AdminService adminService;

//    @Autowired
//    private CustomWebAuthenticationDetailsSource authenticationDetailsSource;

    @Autowired
    @Qualifier("adminUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    AuthenticationSuccessHandlerImpl successHandler;
    @Autowired
    AuthenticationFailureHandlerImpl failureHandler;

    @Bean
    FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource() {
        return new GFFilterInvocationSecurityMetadataSource(MenuType.AdminConsole);
    }

    /**
     * dynamic url설정을 위한 Interceptor
     *
     * @return
     * @throws Exception
     */
    @Bean
    public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(filterInvocationSecurityMetadataSource());
        filterSecurityInterceptor.setAuthenticationManager(authenticationManager());
        filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased());
        try {
            filterSecurityInterceptor.afterPropertiesSet();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return filterSecurityInterceptor;
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers(
                "/modules/**", "/js/**", "/css/**", "/img/**", "/image/**", "/fonts/**",
                "/attachment/**", "/multi-factor/ajax/**", "/excel/**",
                "/healthcheck.html", "/emptyplayer.html", "/robots.txt", "/admin/reset-password/**", "/init-data");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        AdminAuthenticationProvider adminAuthenticationProvider = new AdminAuthenticationProvider(adminService, userDetailsService, passwordEncoder);
        auth.authenticationProvider(adminAuthenticationProvider);
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        log.info("SecurityConfiguration Admin HttpSecurity !");

        // Login & Logout 설정
        http.formLogin()
//                .authenticationDetailsSource(authenticationDetailsSource)
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .loginPage("/login")
                .usernameParameter("username").passwordParameter("password")
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .and().addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class)
                .sessionManagement()
                .maximumSessions(10) //스프링 시큐리티 세션 1개로 설정;
                .expiredUrl("/login");
    }
}
