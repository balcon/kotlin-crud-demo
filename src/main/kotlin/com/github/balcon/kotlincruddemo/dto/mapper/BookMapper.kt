package com.github.balcon.kotlincruddemo.dto.mapper

import com.github.balcon.kotlincruddemo.dto.BookReadDto
import com.github.balcon.kotlincruddemo.dto.BookWithAuthorReadDto
import com.github.balcon.kotlincruddemo.dto.BookWriteDto
import com.github.balcon.kotlincruddemo.model.Author
import com.github.balcon.kotlincruddemo.model.Book
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface BookMapper {
    fun toDto(book: Book): BookReadDto

    fun toDtoWithAuthor(book: Book): BookWithAuthorReadDto

    @Mapping(target = "id", ignore = true)
    fun toEntity(bookDto: BookWriteDto, author: Author): Book
}
