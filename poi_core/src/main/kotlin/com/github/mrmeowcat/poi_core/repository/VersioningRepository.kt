package com.github.mrmeowcat.poi_core.repository

import com.github.mrmeowcat.poi_core.document.AbstractDocument
import java.io.Serializable

/**
 * Interface for document versioning.
 */
interface VersioningRepository<D : AbstractDocument<ID>, ID : Serializable> {

    /**
     * Gets document version.
     */
    fun getVersion(document: D, version: Int): D

    /**
     * Creates document version.
     */
    fun createVersion(document: D)

    /**
     * Deletes document version.
     */
    fun deleteVersion(document: D, version: Int)
}
