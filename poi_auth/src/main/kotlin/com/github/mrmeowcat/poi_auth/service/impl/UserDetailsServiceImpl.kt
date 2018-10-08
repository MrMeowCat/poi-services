package com.github.mrmeowcat.poi_auth.service.impl

import com.github.mrmeowcat.poi_auth.dto.Account
import com.github.mrmeowcat.poi_auth.service.AccountService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * User details service implementation.
 */
@Service
class UserDetailsServiceImpl(private val accountService: AccountService) : UserDetailsService {

    /**
     * Loads user by username.
     */
    override fun loadUserByUsername(username: String): UserDetails {
        if (!accountService.existsByUsername(username)) {
            throw UsernameNotFoundException("username: $username")
        }
        val account: Account = accountService.findByUsername(username)
        val authorities: List<GrantedAuthority> = account.permissions.map {
            SimpleGrantedAuthority(it)
        }
        return User(username, account.password, account.enabled, true, true, true, authorities)
    }
}
