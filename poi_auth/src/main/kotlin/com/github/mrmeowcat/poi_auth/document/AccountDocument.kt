package com.github.mrmeowcat.poi_auth.document

import com.github.mrmeowcat.poi_core.document.AbstractDocument
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

/**
 * User document.
 */
@Document(collection = "accounts")
class AccountDocument : AbstractDocument<String>() {
    /**
     * Username.
     */
    @Indexed(unique = true)
    var username: String? = ""
    /**
     * Email address.
     */
    @Indexed(unique = true)
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
