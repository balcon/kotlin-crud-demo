package com.github.balcon.kotlincruddemo.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private val logger = KotlinLogging.logger { }

@RestControllerAdvice
class ControllerAdvice {
    @ExceptionHandler
    fun handleRuntimeException(exception: EntityNotFoundException): ResponseEntity<String> {
        return exception.message
            ?.let {
                val message = "Entity not found ($it)"
                logger.error { message }
                ResponseEntity(message, HttpStatus.NOT_FOUND)
            } ?: ResponseEntity.notFound().build()
    }

    @ExceptionHandler
    fun handleValidationException(exception: BindException): ResponseEntity<String> {
        logger.error { exception.message }
        return ResponseEntity(exception.bindingResult.toString(), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleException(exception: Exception): ResponseEntity<String> {
        logger.error { exception.toString() }
        return ResponseEntity(exception.toString(), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
