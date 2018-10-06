package com.github.mrmeowcat.poi_core.mapper

import com.github.mrmeowcat.poi_core.document.AbstractDocument
import com.github.mrmeowcat.poi_core.dto.PersistentDto
import java.io.Serializable
import java.util.*

/**
 * Interface for Document/Dto mapper.
 */
interface Document2DtoMapper<Doc : AbstractDocument<ID>, Dto: PersistentDto<ID>, ID : Serializable> {

    /**
     * Maps Document to Dto.
     */
    fun map(document: Doc?): Dto?

    /**
     * Maps Dto to Document.
     */
    fun map(dto: Dto?): Doc? {
        throw UnsupportedOperationException()
    }

    /**
     * Maps persistent properties of document to dto.
     */
    fun mapPersistentProps(dto: Dto?, document: Doc?): Dto? {
        if (dto == null) {
            return null
        }
        if (document == null) {
            return dto
        }
        dto.id = document.id
        dto.createdDate = if (document.createdDate != null) (document.createdDate as Date).time else 0
        dto.modifiedDate = if (document.modifiedDate != null) (document.modifiedDate as Date).time else 0
        dto.version = document.version
        return dto
    }

    /**
     * Maps persistent properties of dto to document.
     */
    fun mapPersistentProps(document: Doc?, dto: Dto?): Doc? {
        if (document == null) {
            return null
        }
        if (dto == null) {
            return document
        }
        document.id = dto.id
        document.createdDate = Date(dto.createdDate ?: 0)
        document.modifiedDate = Date(dto.modifiedDate ?: 0)
        document.version = dto.version
        return document
    }
}
