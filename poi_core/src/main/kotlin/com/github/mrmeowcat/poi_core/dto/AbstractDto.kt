package com.github.mrmeowcat.poi_core.dto

import kotlin.reflect.full.createInstance

/**
 * Abstract dto marker class.
 */
abstract class AbstractDto

/**
 * Constructs Dto.
 */
inline fun <reified D : AbstractDto> dto(block: D.() -> Unit): D {
    return D::class.createInstance().apply(block)
}
