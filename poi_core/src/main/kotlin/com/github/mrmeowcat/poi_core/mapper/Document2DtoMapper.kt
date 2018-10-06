package com.github.mrmeowcat.poi_core.mapper

import com.github.mrmeowcat.poi_core.document.AbstractDocument
import com.github.mrmeowcat.poi_core.dto.PersistentDto
import java.io.Serializable

/**
 * Interface for Document/Dto mapper.
 */
interface Document2DtoMapper<Doc : AbstractDocument<out Serializable>, Dto: PersistentDto<out Serializable>> {

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
}
