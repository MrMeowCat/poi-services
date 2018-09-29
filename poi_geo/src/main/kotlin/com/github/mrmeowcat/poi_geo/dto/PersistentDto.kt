package com.github.mrmeowcat.poi_geo.dto

/**
 * Dto with id property.
 */
abstract class PersistentDto : BaseDto() {
    /**
     * Unique ID.
     */
    var id: String? = null
}
