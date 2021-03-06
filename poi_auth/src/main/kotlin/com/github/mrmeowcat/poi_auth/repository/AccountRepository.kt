package com.github.mrmeowcat.poi_auth.repository

import com.github.mrmeowcat.poi_auth.document.AccountDocument
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

/**
 * Repository for AccountDocument.
 */
interface AccountRepository : MongoRepository<AccountDocument, String> {

    /**
     * Finds Account by username.
     */
    fun findByUsername(username: String?): Optional<AccountDocument>

    /**
     * Finds Account by email.
     */
    fun findByEmail(email: String?): Optional<AccountDocument>

    /**
     * Checks if Account exists by username.
     */
    fun existsByUsername(username: String?): Boolean

    /**
     * Checks if Account exists by email.
     */
    fun existsByEmail(email: String?): Boolean
}
