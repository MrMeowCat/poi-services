package com.github.mrmeowcat.poi_registry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class PoiRegistry

fun main(args: Array<String>) {
    runApplication<PoiRegistry>()
}
