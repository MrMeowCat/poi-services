package com.github.mrmeowcat.poi_auth.exception

import com.github.mrmeowcat.poi_auth.dto.RegistrationResponse

/**
 * Exception thrown when registration failed.
 */
class RegistrationException(val response: RegistrationResponse) : Exception()
