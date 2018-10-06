package com.github.mrmeowcat.poi_core.document

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import java.io.Serializable
import java.util.*
import kotlin.reflect.full.createInstance

/**
 * Abstract persistent document.
 */
abstract class AbstractDocument<ID : Serializable> {
    /**
     * Unique ID.
     */
    @Id
    var id: ID? = null
    /**
     * Date of document creation.
     */
    @CreatedDate
    var createdDate: Date? = null
    /**
     * Date of last modification.
     */
    @LastModifiedDate
    var modifiedDate: Date? = null
    /**
     * Document version.
     */
    @Version
    var version: Int = 0
}

/**
 * Creates document.
 */
inline fun <reified D : AbstractDocument<out Serializable>> document(block: D.() -> Unit): D {
    return D::class.createInstance().apply(block)
}
