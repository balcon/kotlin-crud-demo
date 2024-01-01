package com.github.balcon.kotlincruddemo.dto

import jakarta.validation.constraints.NotBlank

data class BookDto(
    val id: Long? = null,
    @field:NotBlank
    val title: String,
    val year: Int? = null,
    val author: AuthorDto? = null,
)
