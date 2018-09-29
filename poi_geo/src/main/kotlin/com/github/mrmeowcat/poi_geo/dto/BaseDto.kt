package com.github.mrmeowcat.poi_geo.dto

import kotlin.reflect.full.createInstance

/**
 * Base dto marker class.
 */
abstract class BaseDto

/**
 * Constructs Dto.
 */
inline fun <reified D : BaseDto> dto(block: D.() -> Unit): D = D::class.createInstance().apply(block)
