package com.github.balcon.kotlincruddemo.dto.mapper

import com.github.balcon.kotlincruddemo.dto.AuthorDto
import com.github.balcon.kotlincruddemo.model.Author
import org.springframework.stereotype.Component

@Component
class AuthorMapper(
    private val bookMapper: BookMapper,
) {
    fun toDto(author: Author) =
        AuthorDto(
            id = author.id,
            name = author.name,
            country = author.country,
            books = listOf()
        )

    fun toDtoWithBooks(author: Author) =
        AuthorDto(
            id = author.id,
            name = author.name,
            country = author.country,
            books = author.books.map {
                bookMapper.toDto(it)
            })

    fun toEntity(authorDto: AuthorDto) =
        Author(
            name = authorDto.name,
            country = authorDto.country
        )
}
