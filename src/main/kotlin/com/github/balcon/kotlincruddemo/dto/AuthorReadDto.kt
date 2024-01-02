package com.github.balcon.kotlincruddemo.dto

data class AuthorReadDto(
    val id: Long,
    val name: String,
    val country: String?,
)
