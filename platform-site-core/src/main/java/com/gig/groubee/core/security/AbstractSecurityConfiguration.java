package com.gig.groubee.core.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author : Jake
 * @date : 2021-06-06
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractSecurityConfiguration extends WebSecurityConfigurerAdapter {

    protected AuthenticationSuccessHandler successHandler;
    protected AuthenticationFailureHandler failureHandler;

    /**
     * 로그인 의사결정 방식
     * 하나만 통과해도 OK
     *
     * @return
     */
    public AffirmativeBased affirmativeBased() {
        List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
        accessDecisionVoters.add(new RoleVoter());
        AffirmativeBased affirmativeBased = new AffirmativeBased(accessDecisionVoters);
        affirmativeBased.setAllowIfAllAbstainDecisions(false);
        return affirmativeBased;

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/js/**", "/css/**", "/fonts/**", "/images/**");
    }

    /**
     * Admin, Web 에서 각각 auth 재 정의 후 동작
     * 이 부분이 있으면 UserDetailsAuthenticationProvider  로 동작.
     *
     **/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        http.addFilterBefore(characterEncodingFilter, CsrfFilter.class);

    }
}
