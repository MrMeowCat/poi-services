package com.github.mrmeowcat.poi_auth

import com.github.mrmeowcat.poi_auth.document.AccountDocument
import com.github.mrmeowcat.poi_auth.dto.Account
import com.github.mrmeowcat.poi_auth.service.AccountService
import com.github.mrmeowcat.poi_core.document.document
import com.github.mrmeowcat.poi_core.dto.dto
import com.github.mrmeowcat.poi_core.exception.NotFoundException
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataMongoTest
@ComponentScan("com.github.mrmeowcat.poi_auth")
@EnableMongoAuditing
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AccountServiceTests {

    @Autowired
    lateinit var accountService: AccountService

    @Test
    fun test01_clearAll() {
        accountService.deleteAll()
        assertEquals(0, accountService.findAll().size)
    }

    @Test
    fun test02_addAccounts() {
        var account: Account = dto {
            username = "1"
            password = "1"
            email = "1"
        }
        var saved = accountService.save(account)
        Assert.assertNotNull(saved)
        Assert.assertNotNull(saved.createdDate)
        Assert.assertNotNull(saved.modifiedDate)
        saved = accountService.findById(saved.id!!)
        Assert.assertNotNull(saved)

        account = dto {
            username = "2"
            password = "2"
            email = "2"
        }
        saved = accountService.save(account)
        Assert.assertNotNull(saved)
        Assert.assertNotNull(saved.createdDate)
        Assert.assertNotNull(saved.modifiedDate)
        saved = accountService.findById(saved.id!!)
        Assert.assertNotNull(saved)
    }

    @Test
    fun test03_findAccounts() {
        val accounts = accountService.findAll()
        assertEquals(2, accounts.size)
    }

    @Test
    fun test04_findById() {
        val accounts = accountService.findAll()
        val id = accounts[0].id
        val account = accountService.findById(id!!)
        Assert.assertNotNull(account)
    }

    @Test(expected = NotFoundException::class)
    fun test05_findByIdFail() {
        accountService.findById("nonono")
    }

    @Test
    fun test06_findByUsername() {
        val account = accountService.findByUsername("1")
        Assert.assertNotNull(account)
    }

    @Test(expected = NotFoundException::class)
    fun test07_findByIdUsername() {
        accountService.findByUsername(null)
    }

    @Test
    fun test08_findByEmail() {
        val account = accountService.findByEmail("1")
        Assert.assertNotNull(account)
    }

    @Test(expected = NotFoundException::class)
    fun test09_findByIdEmailFail() {
        accountService.findByEmail(null)
    }

    @Test
    fun test10_saveAccount() {
        val accounts = accountService.findAll()
        var account = accounts[0]
        val version = account.version
        account.permissions = listOf("ADMIN", "USER")
        account = accountService.save(account)
        Assert.assertNotEquals(account.createdDate, account.modifiedDate)
        Assert.assertNotEquals(version, account.version)
        assertEquals(2, account.permissions.size)
    }

    @Test
    fun test11_deleteAccount() {
        val accounts = accountService.findAll()
        val id = accounts[0].id
        accountService.delete(id!!)
        assertEquals(accounts.size - 1, accountService.findAll().size)
    }

    @Test
    fun test12_accountExists() {
        val accounts = accountService.findAll()
        val id = accounts[0].id
        Assert.assertTrue(accountService.exists(id!!))
        Assert.assertFalse(accountService.exists("asdasdasd"))
    }
}
