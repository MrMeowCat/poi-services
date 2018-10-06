package com.github.mrmeowcat.poi_geo.dto

import com.github.mrmeowcat.poi_core.dto.AbstractDto

/**
 * Error response.
 */
class ErrorResponse : AbstractDto() {
    /**
     * App-specific error code.
     */
    var code: Int = 0
    /**
     * Error message.
     */
    var message: String? = null
}
