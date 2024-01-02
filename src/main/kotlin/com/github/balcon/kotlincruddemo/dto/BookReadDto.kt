package com.github.balcon.kotlincruddemo.dto

data class BookReadDto(
    val id: Long,
    val title: String,
    val year: Int?,
)
