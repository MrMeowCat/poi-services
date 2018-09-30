package com.github.mrmeowcat.poi_geo.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
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
class OAuth2ResourceConfig : ResourceServerConfigurerAdapter() {

    /**
     * JWT signing secret.
     */
    @Value("\${oauth2.jwt.secret}")
    private lateinit var jwtSecret: String

    /**
     * Configures resources.
     */
    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.tokenStore(JwtTokenStore(jwtAccessTokenConverter()))
    }

    /**
     * Configures http.
     */
    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
    }

    /**
     * Creates JwtAccessTokenConverter Bean.
     */
    @Bean
    fun jwtAccessTokenConverter(): JwtAccessTokenConverter {
        val jwtAccessTokenConverter = JwtAccessTokenConverter()
        jwtAccessTokenConverter.setSigningKey(jwtSecret)
        return jwtAccessTokenConverter
    }
}
