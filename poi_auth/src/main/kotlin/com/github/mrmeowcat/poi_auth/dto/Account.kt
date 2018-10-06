package com.github.mrmeowcat.poi_auth.dto

import com.github.mrmeowcat.poi_core.dto.PersistentDto

/**
 * Account Dto.
 */
class Account : PersistentDto<String>() {
    /**
     * Username.
     */
    var username: String? = ""
    /**
     * Email address.
     */
    var email: String? = ""
    /**
     * Password.
     */
    var password: String? = ""
    /**
     * Account is enabled flag.
     */
    var enabled: Boolean = true
    /**
     * Account permissions.
     */
    var permissions: List<String> = mutableListOf()
}
