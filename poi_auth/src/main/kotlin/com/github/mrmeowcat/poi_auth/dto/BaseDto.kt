package com.github.mrmeowcat.poi_auth.dto

import kotlin.reflect.full.createInstance

abstract class BaseDto {

    var id: String = ""

}

inline fun <reified D : BaseDto> dto(block: D.() -> Unit): D
        = D::class.createInstance().apply(block)
