package com.github.mrmeowcat.poi_auth.service

import com.github.mrmeowcat.poi_auth.dto.Credentials
import com.github.mrmeowcat.poi_auth.dto.RegistrationResponse

/**
 * Service for registration handling.
 */
interface RegistrationService {

    /**
     * Error codes.
     */
    enum class ErrorCodes {
        /**
         * Credentials are empty.
         */
        EMPTY,
        /**
         * Username is empty.
         */
        USERNAME_EMPTY,
        /**
         * Email is empty.
         */
        EMAIL_EMPTY,
        /**
         * Password is empty.
         */
        PASSWORD_EMPTY,
        /**
         * Confirm is empty.
         */
        CONFIRM_EMPTY,
        /**
         * Password and confirm do not match.
         */
        PASSWORD_MISMATCH,
        /**
         * Username is incorrect.
         */
        USERNAME_INCORRECT,
        /**
         * Email is incorrect.
         */
        EMAIL_INCORRECT,
        /**
         * Password is incorrect.
         */
        PASSWORD_INCORRECT,
        /**
         * Username exists.
         */
        USERNAME_EXISTS,
        /**
         * Email exists.
         */
        EMAIL_EXISTS
    }

    /**
     * Registers new Account. Performs validation of credentials.
     */
    fun register(credentials: Credentials?): RegistrationResponse
}
