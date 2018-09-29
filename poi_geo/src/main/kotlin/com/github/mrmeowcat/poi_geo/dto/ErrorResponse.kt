package com.github.mrmeowcat.poi_geo.dto

/**
 * Error response.
 */
class ErrorResponse : BaseDto() {
    /**
     * App-specific error code.
     */
    var code: Int = 0
    /**
     * Error message.
     */
    var message: String? = null
}
