package com.github.mrmeowcat.poi_auth.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

/**
 * Configuration for OAuth2 resource server.
 */
@Configuration
@EnableResourceServer
class OAuth2ResourceConfig(private val jwtAccessTokenConverter: JwtAccessTokenConverter)
    : ResourceServerConfigurerAdapter() {

    /**
     * Configures resources.
     */
    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.tokenStore(JwtTokenStore(jwtAccessTokenConverter))
    }

    /**
     * Configures http.
     */
    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/register").permitAll()
                .anyRequest().authenticated()
    }
}
