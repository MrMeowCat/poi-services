package com.github.mrmeowcat.poi_auth.api.v1

import com.github.mrmeowcat.poi_auth.Response
import com.github.mrmeowcat.poi_auth.dto.Credentials
import com.github.mrmeowcat.poi_auth.dto.RegistrationResponse
import com.github.mrmeowcat.poi_auth.service.RegistrationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller for handling registration requests.
 */
@RestController
@RequestMapping("v1/registration")
class RegistrationController(private val registrationService: RegistrationService) {

    /**
     * Endpoint for account registration.
     */
    @PostMapping
    fun register(@RequestBody credentials: Credentials?): Response<RegistrationResponse> {
        val result: RegistrationResponse = registrationService.register(credentials)
        return Response.ok(result)
    }
}
