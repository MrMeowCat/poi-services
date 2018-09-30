package com.github.mrmeowcat.poi_gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.zuul.EnableZuulProxy

/**
 * Starter class.
 */
@SpringBootApplication
@EnableZuulProxy
class PoiGateway

/**
 * Application entry point.
 */
fun main(args: Array<String>) {
    runApplication<PoiGateway>()
}
