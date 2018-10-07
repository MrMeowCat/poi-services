package com.github.mrmeowcat.poi_auth.service.impl

import com.github.mrmeowcat.poi_auth.dto.Account
import com.github.mrmeowcat.poi_auth.dto.Credentials
import com.github.mrmeowcat.poi_auth.dto.RegistrationResponse
import com.github.mrmeowcat.poi_auth.exception.RegistrationException
import com.github.mrmeowcat.poi_auth.service.AccountService
import com.github.mrmeowcat.poi_auth.service.RegistrationService
import com.github.mrmeowcat.poi_auth.service.RegistrationService.ErrorCodes
import com.github.mrmeowcat.poi_core.dto.dto
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * Implementation of RegistrationService.
 */
@Service
class RegistrationServiceImpl(private val accountService: AccountService,
                              private val passwordEncoder: PasswordEncoder) : RegistrationService {

    companion object {
        /**
         * List of default user permissions.
         */
        private val DEFAULT_PERMISSIONS: List<String> = listOf("USER")
    }

    /**
     * List of forbidden names.
     */
    @Value("\${registration.excludedNames}")
    private lateinit var excludedNames: Array<String>
    /**
     * Username max length.
     */
    @Value("\${registration.nameMaxLength}")
    private var nameMaxLength: Int = 20
    /**
     * Email regex pattern.
     */
    @Value("\${registration.emailPattern}")
    private lateinit var emailPattern: String
    /**
     * Password regex pattern.
     */
    @Value("\${registration.passwordPattern}")
    private lateinit var passwordPattern: String

    override fun register(credentials: Credentials?): RegistrationResponse {
        val response = RegistrationResponse()
        validateCredentials(credentials, response)
        val account: Account = dto {
            username = credentials?.username
            email = credentials?.email
            password = passwordEncoder.encode(credentials?.password)
            permissions = DEFAULT_PERMISSIONS
        }
        accountService.save(account)
        response.success = true
        return response
    }

    /**
     * Validates registration credentials.
     * Checks credentials in three steps: emptiness, correctness, existence.
     */
    private fun validateCredentials(credentials: Credentials?, response: RegistrationResponse) {
        if (credentials == null) {
            response.errors.add(ErrorCodes.EMPTY)
            throw RegistrationException(response)
        }

        /* Check empty fields */
        if (credentials.username.isNullOrBlank()) {
            response.errors.add(ErrorCodes.USERNAME_EMPTY)
        }
        if (credentials.email.isNullOrBlank()) {
            response.errors.add(ErrorCodes.EMAIL_EMPTY)
        }
        if (credentials.password.isNullOrBlank()) {
            response.errors.add(ErrorCodes.PASSWORD_EMPTY)
        }
        if (credentials.confirm.isNullOrBlank()) {
            response.errors.add(ErrorCodes.CONFIRM_EMPTY)
        }

        if (response.errors.size > 0) {
            throw RegistrationException(response)
        }

        /* Check correctness and existence */
        if (!isCorrectUsername(credentials.username!!)) {
            response.errors.add(ErrorCodes.USERNAME_INCORRECT)
        }
        if (!isCorrectEmail(credentials.email!!)) {
            response.errors.add(ErrorCodes.EMAIL_INCORRECT)
        }
        if (!isCorrectPassword(credentials.password!!)) {
            response.errors.add(ErrorCodes.PASSWORD_INCORRECT)
        }
        if (credentials.password != credentials.confirm) {
            response.errors.add(ErrorCodes.PASSWORD_MISMATCH)
        }
        if (accountService.existsByUsername(credentials.username)) {
            response.errors.add(ErrorCodes.USERNAME_EXISTS)
        }
        if (accountService.existsByEmail(credentials.email)) {
            response.errors.add(ErrorCodes.EMAIL_EXISTS)
        }

        if (response.errors.size > 0) {
            throw RegistrationException(response)
        }
    }

    /**
     * Checks if username is correct.
     */
    private fun isCorrectUsername(username: String): Boolean {
        return !excludedNames.contains(username.toLowerCase()) && username.length <= nameMaxLength
    }

    /**
     * Checks if email is correct.
     */
    private fun isCorrectEmail(email: String): Boolean {
        return emailPattern.toRegex().matches(email)
    }

    /**
     * Checks if password is correct.
     */
    private fun isCorrectPassword(password: String): Boolean {
        return passwordPattern.toRegex().matches(password)
    }
}
