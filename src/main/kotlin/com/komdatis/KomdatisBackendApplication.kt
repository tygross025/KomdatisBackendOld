package com.komdatis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KomdatisBackendApplication

fun main(args: Array<String>) {
    runApplication<KomdatisBackendApplication>(*args)
}
