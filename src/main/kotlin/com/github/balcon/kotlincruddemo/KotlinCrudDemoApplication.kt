package com.github.balcon.kotlincruddemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinCrudDemoApplication

fun main(args: Array<String>) {
    runApplication<KotlinCrudDemoApplication>(*args)
}
