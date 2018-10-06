package com.github.mrmeowcat.poi_auth.document

import com.github.mrmeowcat.poi_core.annotation.EnableVersioning
import com.github.mrmeowcat.poi_core.document.AbstractDocument
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

/**
 * User account document.
 */
@Document(collection = "accounts")
@EnableVersioning(collection = "accounts_versions")
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
