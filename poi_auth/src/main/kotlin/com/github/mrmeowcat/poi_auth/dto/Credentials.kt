package com.github.mrmeowcat.poi_auth.dto

import com.github.mrmeowcat.poi_core.dto.AbstractDto

/**
 * Account credentials.
 */
class Credentials : AbstractDto() {
    /**
     * Account name.
     */
    var username: String? = null
    /**
     * Account email.
     */
    var email: String? = null
    /**
     * Account password.
     */
    var password: String? = null
    /**
     * Confirmation of password.
     */
    var confirm: String? = null
}
