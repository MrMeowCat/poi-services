package com.github.mrmeowcat.poi_auth.repository.versioning

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.mrmeowcat.poi_core.annotation.EnableVersioning
import com.github.mrmeowcat.poi_core.document.AbstractDocument
import com.github.mrmeowcat.poi_core.exception.VersionNotFoundException
import com.github.mrmeowcat.poi_core.exception.VersioningNotEnabledException
import com.github.mrmeowcat.poi_core.repository.VersioningRepository
import com.mongodb.BasicDBObject
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.io.Serializable
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.jvmName

/**
 * Default implementation of VersioningRepository for MongoDB.
 */
@Repository
abstract class AbstractVersioningRepository<D : AbstractDocument<ID>, ID : Serializable>
    : VersioningRepository<D, ID> {

    /**
     * Document fields.
     */
    private companion object {
        private const val MONGO_ID_FIELD = "_id"
        private const val ID_FIELD = "id"
        private const val VERSION_FIELD = "version"
    }

    /**
     * Mongo template.
     */
    @Autowired
    private lateinit var mongoTemplate: MongoTemplate
    /**
     * Jackson object mapper.
     */
    private val objectMapper = ObjectMapper()

    override fun getVersion(document: D, version: Int): D {
        val collection: String = getCollection(document)
        val query = Query()
        query.addCriteria(Criteria.where(ID_FIELD).`is`(document.id))
        query.addCriteria(Criteria.where(VERSION_FIELD).`is`(version))
        val versionDocument: Document? = mongoTemplate.findOne(query, Document::class.java, collection)
        versionDocument ?: throw VersionNotFoundException("id: ${document.id}, version: $version")
        versionDocument.remove(MONGO_ID_FIELD)
        return objectMapper.convertValue(versionDocument, document::class.java)
    }

    override fun createVersion(document: D) {
        val collection: String = getCollection(document)
        val obj: BasicDBObject = objectMapper.convertValue(document, BasicDBObject::class.java)
        mongoTemplate.insert(obj, collection)
    }

    override fun deleteVersion(document: D, version: Int) {
        val collection: String = getCollection(document)
        val query = Query()
        query.addCriteria(Criteria.where(ID_FIELD).`is`(document.id))
        query.addCriteria(Criteria.where(VERSION_FIELD).`is`(version))
        if (mongoTemplate.remove(query, collection).deletedCount == 0L) {
            throw VersionNotFoundException("id: ${document.id}, version: $version")
        }
    }

    /**
     * Retrieves collection name from document.
     */
    private fun getCollection(document: D): String {
        val annotation: EnableVersioning? = document::class.findAnnotation()
        annotation ?: throw VersioningNotEnabledException(document::class.jvmName)
        return annotation.collection
    }
}
