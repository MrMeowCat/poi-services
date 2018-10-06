package com.github.mrmeowcat.poi_auth.service

import com.github.mrmeowcat.poi_auth.dto.Account
import com.github.mrmeowcat.poi_core.service.CrudService

/**
 * Service for Account.
 */
interface AccountService : CrudService<Account, String> {

    /**
     * Finds Account by username.
     */
    fun findByUsername(username: String?): Account

    /**
     * Finds Account by email.
     */
    fun findByEmail(email: String?): Account
}
