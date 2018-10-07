package com.github.mrmeowcat.poi_auth

import com.github.mrmeowcat.poi_auth.document.AccountDocument
import com.github.mrmeowcat.poi_auth.repository.AccountRepository
import com.github.mrmeowcat.poi_core.document.document
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataMongoTest
@EnableMongoAuditing
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AccountRepositoryTests {

    @Autowired
    lateinit var accountRepository: AccountRepository

    @Test
    fun test01_clearAll() {
        accountRepository.deleteAll()
        assertEquals(0, accountRepository.findAll().size)
    }

    @Test
    fun test02_addAccounts() {
        var account: AccountDocument = document {
            username = "1"
            password = "1"
            email = "1"
        }
        var saved = accountRepository.save(account)
        assertNotNull(saved)
        assertNotNull(saved.createdDate)
        assertNotNull(saved.modifiedDate)
        saved = accountRepository.findById(saved.id!!).get()
        assertNotNull(saved)

        account = document {
            username = "2"
            password = "2"
            email = "2"
        }
        saved = accountRepository.save(account)
        assertNotNull(saved)
        assertNotNull(saved.createdDate)
        assertNotNull(saved.modifiedDate)
        saved = accountRepository.findById(saved.id!!).get()
        assertNotNull(saved)
    }

    @Test
    fun test03_findAccounts() {
        val accounts = accountRepository.findAll()
        assertEquals(2, accounts.size)
    }

    @Test
    fun test04_findById() {
        val accounts = accountRepository.findAll()
        val id = accounts[0].id
        val account = accountRepository.findById(id!!).get()
        assertNotNull(account)
    }

    @Test(expected = NoSuchElementException::class)
    fun test05_findByIdFail() {
        accountRepository.findById("nonono").get()
    }

    @Test
    fun test06_findByUsername() {
        val account = accountRepository.findByUsername("1").get()
        assertNotNull(account)
    }

    @Test(expected = NoSuchElementException::class)
    fun test07_findByIdUsername() {
        accountRepository.findByUsername(null).get()
    }

    @Test
    fun test08_findByEmail() {
        val account = accountRepository.findByEmail("1").get()
        assertNotNull(account)
    }

    @Test(expected = NoSuchElementException::class)
    fun test09_findByIdEmailFail() {
        accountRepository.findByEmail(null).get()
    }

    @Test
    fun test10_existsByUsername() {
        assertTrue(accountRepository.existsByUsername("1"))
        assertFalse(accountRepository.existsByUsername("3"))
    }

    @Test
    fun test11_existsByEmail() {
        assertTrue(accountRepository.existsByEmail("1"))
        assertFalse(accountRepository.existsByEmail("3"))
    }

    @Test
    fun test12_saveAccount() {
        val accounts = accountRepository.findAll()
        var account = accounts[0]
        val version = account.version
        account.permissions = listOf("ADMIN", "USER")
        account = accountRepository.save(account)
        assertNotEquals(account.createdDate, account.modifiedDate)
        assertNotEquals(version, account.version)
        assertEquals(2, account.permissions.size)
    }

    @Test
    fun test13_deleteAccount() {
        val accounts = accountRepository.findAll()
        val id = accounts[0].id
        accountRepository.deleteById(id!!)
        assertEquals(accounts.size - 1, accountRepository.findAll().size)
    }

    @Test
    fun test14_accountExists() {
        val accounts = accountRepository.findAll()
        val id = accounts[0].id
        assertTrue(accountRepository.existsById(id!!))
        assertFalse(accountRepository.existsById("asdasdasd"))
    }
}
