package com.github.balcon.kotlincruddemo.dto

import jakarta.validation.constraints.NotBlank

data class AuthorWriteDto(
    @field:NotBlank
    val name: String,
    val country: String? = null,
)
