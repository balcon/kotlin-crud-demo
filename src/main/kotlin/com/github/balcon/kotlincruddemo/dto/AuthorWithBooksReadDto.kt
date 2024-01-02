package com.github.balcon.kotlincruddemo.dto

data class AuthorWithBooksReadDto(
    val id: Long,
    val name: String,
    val country: String?,
    val books: List<BookReadDto>
)
