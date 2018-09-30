package com.github.mrmeowcat.poi_auth.config

import com.github.mrmeowcat.poi_auth.service.impl.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * Web security Configuration.
 */
@Configuration
@EnableWebSecurity
class SecurityConfig(private val userDetailsService: UserDetailsServiceImpl)
    : WebSecurityConfigurerAdapter() {

    /**
     * Configures userDetailsService.
     */
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }

    /**
     * Creates AuthenticationManager Bean.
     */
    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    /**
     * Creates PasswordEncoder Bean.
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
