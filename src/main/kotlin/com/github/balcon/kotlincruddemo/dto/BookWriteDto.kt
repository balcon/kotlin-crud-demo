package com.github.balcon.kotlincruddemo.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class BookWriteDto(
    @field:NotBlank
    val title: String,
    val year: Int? = null,
    @field:NotNull
    val authorId: Long
)
