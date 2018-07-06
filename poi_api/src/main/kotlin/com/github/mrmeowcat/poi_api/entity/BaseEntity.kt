package com.github.mrmeowcat.poi_api.entity

import org.springframework.data.annotation.Id
import kotlin.reflect.full.createInstance

abstract class BaseEntity {

    @Id
    var id: String? = null

}

inline fun <reified E : BaseEntity> entity(block: E.() -> Unit): E
        = E::class.createInstance().apply(block)
