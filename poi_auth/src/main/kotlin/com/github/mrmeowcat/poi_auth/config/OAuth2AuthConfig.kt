package com.github.mrmeowcat.poi_auth.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

/**
 * OAuth2 Authorization server configuration.
 */
@Configuration
@EnableAuthorizationServer
class OAuth2AuthConfig(private val authenticationManager: AuthenticationManager,
                       private val passwordEncoder: PasswordEncoder)
    : AuthorizationServerConfigurerAdapter() {

    /**
     * JWT signing secret.
     */
    @Value("\${oauth2.jwt.secret}")
    private lateinit var jwtSecret: String
    /**
     * JWT access token age.
     */
    @Value("\${oauth2.jwt.accessTokenAge}")
    private var accessTokenAge: Int = 0
    /**
     * OAuth2 client id.
     */
    @Value("\${oauth2.client.id}")
    private lateinit var clientId: String
    /**
     * OAuth2 client secret.
     */
    @Value("\${oauth2.client.secret}")
    private lateinit var clientSecret: String
    /**
     * OAuth2 grant types.
     */
    @Value("\${oauth2.client.grantTypes}")
    private lateinit var grantTypes: Array<String>
    /**
     * OAuth2 scopes.
     */
    @Value("\${oauth2.client.scopes}")
    private lateinit var scopes: Array<String>

    /**
     * Configures endpoints.
     */
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
                .tokenStore(JwtTokenStore(jwtAccessTokenConverter()))
                .accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager)
    }

    /**
     * Configures security.
     */
    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.allowFormAuthenticationForClients()
    }

    /**
     * Configures clients.
     */
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients
                .inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(clientSecret))
                .authorizedGrantTypes(*grantTypes)
                .scopes(*scopes)
                .accessTokenValiditySeconds(accessTokenAge)
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
