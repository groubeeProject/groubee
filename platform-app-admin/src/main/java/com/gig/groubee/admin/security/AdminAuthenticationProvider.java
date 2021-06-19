package com.gig.groubee.admin.security;

import com.gig.groubee.common.exception.NotFoundException;
import com.gig.groubee.core.model.Admin;
import com.gig.groubee.core.service.AdminService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : Jake
 * @date : 2021-06-19
 */
public class AdminAuthenticationProvider extends DaoAuthenticationProvider {

    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;

    public AdminAuthenticationProvider(AdminService adminService, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(this.passwordEncoder);
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
//        String verificationCode = ((CustomWebAuthenticationDetails) auth.getDetails()).getVerificationCode();
//        String verificationType = ((CustomWebAuthenticationDetails) auth.getDetails()).getVerificationType();

        Admin admin;
        try {
            admin = adminService.getUser(auth.getName());

            if (!admin.getPassword().equals(auth.getCredentials())) {
                throw new BadCredentialsException("Invalid username or password");
            }

//            if (StringUtils.hasText(verificationCode) && StringUtils.hasText(verificationType)) {
//                adminService.multiFactorAuth(admin, verificationCode, verificationType);
//            }

        } catch (NotFoundException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

        Authentication result = super.authenticate(auth);

        return new UsernamePasswordAuthenticationToken(result.getPrincipal(), result.getCredentials(), result.getAuthorities());
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

        String presentedPassword = authentication.getCredentials().toString();

        if (presentedPassword == null || userDetails.getPassword() == null) {
            throw new BadCredentialsException("Credentials may not be null.");
        }

        if (!userDetails.getPassword().equals(presentedPassword)) {
            throw new BadCredentialsException("Invalid credentials.");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
