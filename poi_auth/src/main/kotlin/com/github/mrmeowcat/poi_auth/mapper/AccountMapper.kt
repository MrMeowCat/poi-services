package com.github.mrmeowcat.poi_auth.mapper

import com.github.mrmeowcat.poi_auth.document.AccountDocument
import com.github.mrmeowcat.poi_auth.dto.Account
import com.github.mrmeowcat.poi_core.document.document
import com.github.mrmeowcat.poi_core.dto.dto
import com.github.mrmeowcat.poi_core.mapper.Document2DtoMapper
import org.springframework.stereotype.Component

/**
 * Mapper for Account.
 */
@Component
class AccountMapper : Document2DtoMapper<AccountDocument, Account, String> {

    override fun map(document: AccountDocument?): Account? {
        if (document == null) {
            return null
        }
        val dto: Account = dto {
            username = document.username
            email = document.email
            password = document.password
            enabled = document.enabled
            permissions = document.permissions
        }
        return mapPersistentProps(dto, document)
    }

    override fun map(dto: Account?): AccountDocument? {
        if (dto == null) {
            return null
        }
        val document: AccountDocument = document {
            username = dto.username
            email = dto.email
            password = dto.password
            enabled = dto.enabled
            permissions = dto.permissions
        }
        return mapPersistentProps(document, dto)
    }
}
