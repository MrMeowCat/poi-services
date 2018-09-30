package com.github.mrmeowcat.poi_auth.service.impl

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

/**
 * User details service implementation.
 */
@Service
class UserDetailsServiceImpl : UserDetailsService {

    /**
     * Loads user by username.
     */
    override fun loadUserByUsername(username: String): UserDetails {
        return if (username == "admin") {
            User(username, BCryptPasswordEncoder().encode("password"), listOf(SimpleGrantedAuthority("ROLE_ADMIN")))
        }
        else throw UsernameNotFoundException("User not found!")
    }
}
