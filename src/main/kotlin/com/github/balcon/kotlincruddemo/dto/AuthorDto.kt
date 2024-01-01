package com.github.balcon.kotlincruddemo.dto

import jakarta.validation.constraints.NotBlank

data class AuthorDto(
    val id: Long? = null,
    @field:NotBlank
    val name: String,
    val country: String? = null,
    val books: List<BookDto>? = null
)
