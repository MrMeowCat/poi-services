package com.github.mrmeowcat.poi_auth.api.v1

import com.github.mrmeowcat.poi_auth.Response
import com.github.mrmeowcat.poi_auth.dto.RegistrationResponse
import com.github.mrmeowcat.poi_auth.exception.RegistrationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * Global exception handler.
 */
@ControllerAdvice
class GlobalExceptionHandler {

    /**
     * Handles RegistrationException.
     */
    @ExceptionHandler(RegistrationException::class)
    fun handleRegistrationException(e: RegistrationException): Response<RegistrationResponse> {
        return Response.ok(e.response)
    }
}
