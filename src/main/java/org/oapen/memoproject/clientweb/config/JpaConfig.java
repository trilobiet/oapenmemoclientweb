package org.oapen.memoproject.clientweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
	basePackages = "org.oapen.memoproject.clientweb"
)
public class JpaConfig {}
