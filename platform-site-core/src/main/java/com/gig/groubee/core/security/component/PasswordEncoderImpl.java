package com.gig.groubee.core.security.component;

import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
@Component
public class PasswordEncoderImpl implements PasswordEncoder {

    DelegatingPasswordEncoder passwordEncoder;

    public PasswordEncoderImpl() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        String idForEncode = "SHA-512";
        encoders.put(idForEncode, new MessageDigestPasswordEncoder(idForEncode));
        passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (!encodedPassword.contains("{SHA-512}"))
            encodedPassword = "{SHA-512}" + encodedPassword;
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
