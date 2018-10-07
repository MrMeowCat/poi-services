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
    enum class ErrorCodes(val code: Int) {
        /**
         * Credentials are empty.
         */
        EMPTY(1),
        /**
         * Username is empty.
         */
        USERNAME_EMPTY(2),
        /**
         * Email is empty.
         */
        EMAIL_EMPTY(3),
        /**
         * Password is empty.
         */
        PASSWORD_EMPTY(4),
        /**
         * Confirm is empty.
         */
        CONFIRM_EMPTY(5),
        /**
         * Password and confirm do not match.
         */
        PASSWORD_MISMATCH(6),
        /**
         * Username is incorrect.
         */
        USERNAME_INCORRECT(7),
        /**
         * Email is incorrect.
         */
        EMAIL_INCORRECT(8),
        /**
         * Password is incorrect.
         */
        PASSWORD_INCORRECT(9),
        /**
         * Username exists.
         */
        USERNAME_EXISTS(10),
        /**
         * Email exists.
         */
        EMAIL_EXISTS(11),
    }

    /**
     * Registers new Account. Performs validation of credentials.
     */
    fun register(credentials: Credentials?): RegistrationResponse
}
