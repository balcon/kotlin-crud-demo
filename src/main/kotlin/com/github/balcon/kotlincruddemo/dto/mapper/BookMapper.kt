package com.github.balcon.kotlincruddemo.dto.mapper

import com.github.balcon.kotlincruddemo.dto.BookDto
import com.github.balcon.kotlincruddemo.model.Author
import com.github.balcon.kotlincruddemo.model.Book
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
class BookMapper(
    @Lazy
    private val authorMapper: AuthorMapper
) {
    fun toDto(book: Book) =
        BookDto(
            id = book.id,
            title = book.title,
            year = book.year,
            author = null
        )

    fun toDtoWithAuthor(book: Book) =
        BookDto(
            id = book.id,
            title = book.title,
            year = book.year,
            author = authorMapper.toDto(book.author)
        )

    fun toEntity(bookDto: BookDto, author: Author) =
        Book(
            title = bookDto.title,
            year = bookDto.year,
            author = author
        )
}
