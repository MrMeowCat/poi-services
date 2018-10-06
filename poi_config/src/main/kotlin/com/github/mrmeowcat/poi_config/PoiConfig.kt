package com.github.mrmeowcat.poi_config

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

/**
 * Starter class.
 */
@SpringBootApplication
@EnableConfigServer
class PoiConfig

/**
 * Application entry point.
 */
fun main(args: Array<String>) {
    runApplication<PoiConfig>()
}
