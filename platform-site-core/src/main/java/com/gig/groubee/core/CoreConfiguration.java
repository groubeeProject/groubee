package com.gig.groubee.core;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
@Configuration
@EntityScan("com.gig.groubee.core.model")
@ComponentScan({"com.gig.groubee.core.dto", "com.gig.groubee.core.service", "com.gig.groubee.core.security",
        "com.gig.groubee.core.util", "com.gig.groubee.core.config", "com.gig.groubee.core.repository.querydsl"})
@EnableJpaRepositories("com.gig.groubee.core.repository")
public class CoreConfiguration {
}
