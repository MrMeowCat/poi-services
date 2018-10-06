package com.github.mrmeowcat.poi_core.service

import com.github.mrmeowcat.poi_core.document.AbstractDocument
import com.github.mrmeowcat.poi_core.dto.PersistentDto
import com.github.mrmeowcat.poi_core.exception.NotFoundException
import com.github.mrmeowcat.poi_core.mapper.Document2DtoMapper
import org.springframework.data.repository.CrudRepository
import java.io.Serializable

/**
 * Default implementation of CRUD Service.
 */
abstract class AbstractCrudService<Doc : AbstractDocument<ID>, Dto : PersistentDto<ID>, ID : Serializable>(
        private val repository: CrudRepository<Doc, ID>,
        private val mapper: Document2DtoMapper<Doc, Dto>)
    : CrudService<Dto, ID> {

    override fun findById(id: ID?): Dto {
        if (id == null) {
            throw NotFoundException()
        }
        val document: Doc = repository.findById(id).orElseThrow { NotFoundException() }
        return mapper.map(document)!!
    }

    override fun exists(id: ID?): Boolean {
        return if (id == null) false else repository.existsById(id)
    }

    override fun exists(dto: Dto): Boolean {
        return if (dto.id == null) false else repository.existsById(dto.id as ID)
    }

    override fun save(dto: Dto): Dto {
        val document: Doc = mapper.map(dto)!!
        val savedDocument: Doc = repository.save(document)
        return mapper.map(savedDocument)!!
    }

    override fun delete(id: ID?) {
        if (!exists(id)) {
            throw NotFoundException()
        }
        repository.deleteById(id!!)
    }

    override fun delete(dto: Dto) {
        if (!exists(dto)) {
            throw NotFoundException()
        }
        repository.deleteById(dto.id!!)
    }
}
