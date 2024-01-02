package com.github.balcon.kotlincruddemo.dto

data class BookWithAuthorReadDto(
    val id: Long,
    val title: String,
    val year: Int?,
    val author: AuthorReadDto
)
