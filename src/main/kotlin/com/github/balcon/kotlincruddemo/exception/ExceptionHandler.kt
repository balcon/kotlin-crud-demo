package com.github.balcon.kotlincruddemo.exception

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

private val logger = KotlinLogging.logger { }

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleRuntimeException(exception: ResourceNotExistsException): String {
        logger.warn { exception.message }
        return exception.message ?: "Resource not found"
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(exception: BindException): String {
        logger.warn { exception.message }
        return exception.bindingResult.toString()
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(exception: Exception): String {
        logger.error { exception.toString() }
        return exception.message ?: exception.toString()
    }
}
