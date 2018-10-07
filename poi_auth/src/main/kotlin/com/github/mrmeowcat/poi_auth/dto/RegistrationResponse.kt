package com.github.mrmeowcat.poi_auth.dto

import com.github.mrmeowcat.poi_auth.service.RegistrationService
import com.github.mrmeowcat.poi_core.dto.AbstractDto

/**
 * Registration response.
 */
class RegistrationResponse : AbstractDto() {
    /**
     * Registration is successful.
     */
    var success: Boolean = false
    /**
     * List of errors.
     */
    var errors: MutableList<RegistrationService.ErrorCodes> = mutableListOf()
}
