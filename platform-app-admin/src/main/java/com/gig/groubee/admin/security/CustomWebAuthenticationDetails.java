package com.gig.groubee.admin.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : Jake
 * @date : 2021-06-09
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

    private String verificationCode;
    private String verificationType;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        verificationCode = request.getParameter("code");
        verificationType = request.getParameter("type");
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public String getVerificationType() {
        return verificationType;
    }
}

