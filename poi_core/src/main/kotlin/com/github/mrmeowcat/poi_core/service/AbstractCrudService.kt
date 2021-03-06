package com.github.mrmeowcat.poi_core.service

import com.github.mrmeowcat.poi_core.document.AbstractDocument
import com.github.mrmeowcat.poi_core.dto.PersistentDto
import com.github.mrmeowcat.poi_core.exception.DocumentNotFoundException
import com.github.mrmeowcat.poi_core.mapper.Document2DtoMapper
import org.springframework.data.repository.CrudRepository
import java.io.Serializable

/**
 * Default implementation of CRUD Service.
 */
abstract class AbstractCrudService<Doc : AbstractDocument<ID>, Dto : PersistentDto<ID>, ID : Serializable>(
        private val repository: CrudRepository<Doc, ID>,
        private val mapper: Document2DtoMapper<Doc, Dto, ID>)
    : CrudService<Dto, ID> {

    override fun findAll(): List<Dto> {
        val documents: List<Doc> = repository.findAll().toList()
        return documents.map { mapper.map(it)!! }
    }

    override fun findById(id: ID?): Dto {
        if (id == null) {
            throw DocumentNotFoundException("id: $id")
        }
        val document: Doc = repository.findById(id).orElseThrow { DocumentNotFoundException("id: $id") }
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
            throw DocumentNotFoundException("id: $id")
        }
        repository.deleteById(id!!)
    }

    override fun delete(dto: Dto) {
        if (!exists(dto)) {
            throw DocumentNotFoundException("id: ${dto.id}")
        }
        repository.deleteById(dto.id!!)
    }

    override fun deleteAll() {
        repository.deleteAll()
    }
}
