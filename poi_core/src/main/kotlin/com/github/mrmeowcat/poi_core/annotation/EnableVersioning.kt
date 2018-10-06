package com.github.mrmeowcat.poi_core.annotation

/**
 * Annotation used to mark Document available for versioning.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class EnableVersioning(val collection: String)
