package com.github.mrmeowcat.poi_core.service

import com.github.mrmeowcat.poi_core.dto.PersistentDto
import java.io.Serializable

/**
 * Service for basic CRUD operations.
 */
interface CrudService<D : PersistentDto<ID>, ID : Serializable> {

    /**
     * Finds a record by ID.
     */
    fun findById(id: ID?): D

    /**
     * Checks if a record exists by ID.
     */
    fun exists(id: ID?): Boolean

    /**
     * Checks if a record exists.
     */
    fun exists(dto: D): Boolean

    /**
     * Saves a record.
     */
    fun save(dto: D): D

    /**
     * Deletes a record by ID.
     */
    fun delete(id: ID?)

    /**
     * Deletes a record.
     */
    fun delete(dto: D)
}
