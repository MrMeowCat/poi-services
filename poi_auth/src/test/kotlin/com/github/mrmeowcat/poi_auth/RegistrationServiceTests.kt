package com.github.mrmeowcat.poi_auth

import com.github.mrmeowcat.poi_auth.document.AccountDocument
import com.github.mrmeowcat.poi_auth.dto.Credentials
import com.github.mrmeowcat.poi_auth.dto.RegistrationResponse
import com.github.mrmeowcat.poi_auth.exception.RegistrationException
import com.github.mrmeowcat.poi_auth.repository.AccountRepository
import com.github.mrmeowcat.poi_auth.repository.versioning.AccountVersioningRepository
import com.github.mrmeowcat.poi_auth.service.AccountService
import com.github.mrmeowcat.poi_auth.service.RegistrationService
import com.github.mrmeowcat.poi_auth.service.RegistrationService.ErrorCodes
import com.github.mrmeowcat.poi_core.dto.dto
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataMongoTest
@ComponentScan("com.github.mrmeowcat.poi_auth")
@EnableMongoAuditing
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RegistrationServiceTests {

    @Autowired
    lateinit var registrationService: RegistrationService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var accountVersioningRepository: AccountVersioningRepository

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    var testUsername: String = "test"

    var testEmail: String = "oooooo@oo.oo"

    var testPassword: String = "Pas@w0RD666"

    @Test
    fun test01_setup() {
        accountService.deleteAll()
        Assert.assertEquals(0, accountService.findAll().size)
        mongoTemplate.remove(Query(), "accounts_versions")
        accountService.save(dto {
            username = testUsername
            email = testEmail
            password = passwordEncoder.encode(testPassword)
        })
    }

    @Test(expected = RegistrationException::class)
    fun test02_registerEmpty() {
        val credentials: Credentials? = null
        registrationService.register(credentials)
    }

    @Test
    fun test03_registerEmptyFields() {
        var credentials: Credentials = dto { }
        try {
            registrationService.register(credentials)
        } catch (e: RegistrationException) {
            assertEquals(4, e.response.errors.size)
        }
        credentials = dto {
            username = "1219dj1"
            email = "             "
            password = "129120391023"
            confirm = "1d21p2dp1"
        }
        try {
            registrationService.register(credentials)
        } catch (e: RegistrationException) {
            assertEquals(1, e.response.errors.size)
            assertEquals(ErrorCodes.EMAIL_EMPTY, e.response.errors[0])
        }
    }

    @Test
    fun test04_registerIncorrectUsername() {
        val credentials: Credentials = dto {
            username = "null"
            email = "asdf"
            password = "129120391023"
            confirm = "1d21p2dp1"
        }
        try {
            registrationService.register(credentials)
        } catch (e: RegistrationException) {
            assertTrue(e.response.errors.contains(ErrorCodes.USERNAME_INCORRECT))
        }
        credentials.username = "00000000000000000000000000000000000000000000000000000000000000"
        try {
            registrationService.register(credentials)
        } catch (e: RegistrationException) {
            assertTrue(e.response.errors.contains(ErrorCodes.USERNAME_INCORRECT))
        }
    }

    @Test
    fun test05_registerIncorrectEmail() {
        val credentials: Credentials = dto {
            username = testUsername
            email = "222"
            password = testPassword
            confirm = testPassword
        }
        try {
            registrationService.register(credentials)
        } catch (e: RegistrationException) {
            assertTrue(e.response.errors.contains(ErrorCodes.EMAIL_INCORRECT))
        }
        credentials.email = "abcd@fegh."
        try {
            registrationService.register(credentials)
        } catch (e: RegistrationException) {
            assertTrue(e.response.errors.contains(ErrorCodes.EMAIL_INCORRECT))
        }
    }

    @Test
    fun test06_registerIncorrectPassword() {
        val credentials: Credentials = dto {
            username = testUsername
            email = testEmail
            password = "0"
            confirm = "1"
        }
        try {
            registrationService.register(credentials)
        } catch (e: RegistrationException) {
            assertTrue(e.response.errors.contains(ErrorCodes.PASSWORD_MISMATCH))
        }
        credentials.password = "12345"
        try {
            registrationService.register(credentials)
        } catch (e: RegistrationException) {
            assertTrue(e.response.errors.contains(ErrorCodes.PASSWORD_INCORRECT))
        }
    }

    @Test
    fun test07_registerExists() {
        val credentials: Credentials = dto {
            username = testUsername
            email = testEmail
            password = testPassword
            confirm = testPassword
        }
        try {
            registrationService.register(credentials)
        } catch (e: RegistrationException) {
            assertTrue(e.response.errors.contains(ErrorCodes.USERNAME_EXISTS))
            assertTrue(e.response.errors.contains(ErrorCodes.EMAIL_EXISTS))
        }
    }

    @Test
    fun test08_registerSuccess() {
        val credentials: Credentials = dto {
            username = "${testUsername}1"
            email = "${testEmail}1"
            password = testPassword
            confirm = testPassword
        }
        val response: RegistrationResponse = registrationService.register(credentials)
        assertTrue(response.success)
        val account: AccountDocument = accountRepository.findByUsername(credentials.username).get()
        assertNotNull(account)
        val version: AccountDocument = accountVersioningRepository.getVersion(account, 0)
        assertNotNull(version)
    }
}
