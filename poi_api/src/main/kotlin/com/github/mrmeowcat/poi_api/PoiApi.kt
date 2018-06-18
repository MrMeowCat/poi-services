package com.github.mrmeowcat.poi_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class PoiApi

fun main(args: Array<String>) {
    runApplication<PoiApi>()
}
