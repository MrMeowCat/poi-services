package com.github.mrmeowcat.poi_core.dto

import java.io.Serializable

/**
 * Dto mapped to document.
 */
abstract class PersistentDto<ID : Serializable> : AbstractDto() {
    /**
     * Unique ID.
     */
    var id: ID? = null
    /**
     * Date of document creation.
     */
    var createdDate: Long? = null
    /**
     * Date of last modification.
     */
    var modifiedDate: Long? = null
    /**
     * Document version.
     */
    var version: Int = 0
}
