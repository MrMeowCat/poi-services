package com.github.mrmeowcat.poi_auth.service.impl

import com.github.mrmeowcat.poi_auth.document.AccountDocument
import com.github.mrmeowcat.poi_auth.dto.Account
import com.github.mrmeowcat.poi_auth.mapper.AccountMapper
import com.github.mrmeowcat.poi_auth.repository.AccountRepository
import com.github.mrmeowcat.poi_auth.service.AccountService
import com.github.mrmeowcat.poi_core.exception.NotFoundException
import com.github.mrmeowcat.poi_core.service.AbstractCrudService
import org.springframework.stereotype.Service

/**
 * Implementation of AccountService.
 */
@Service
class AccountServiceImpl(
        private val accountRepository: AccountRepository,
        private val accountMapper: AccountMapper
) : AccountService, AbstractCrudService<AccountDocument, Account, String>(
        accountRepository, accountMapper) {

    override fun findByUsername(username: String?): Account {
        val account: AccountDocument = accountRepository
                .findByUsername(username)
                .orElseThrow { NotFoundException() }
        return accountMapper.map(account)!!
    }

    override fun findByEmail(email: String?): Account {
        val account: AccountDocument = accountRepository
                .findByEmail(email)
                .orElseThrow { NotFoundException() }
        return accountMapper.map(account)!!
    }
}
