package com.github.balcon.kotlincruddemo.dto.mapper

import com.github.balcon.kotlincruddemo.dto.AuthorReadDto
import com.github.balcon.kotlincruddemo.dto.AuthorWithBooksReadDto
import com.github.balcon.kotlincruddemo.dto.AuthorWriteDto
import com.github.balcon.kotlincruddemo.model.Author
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface AuthorMapper {
    fun toDto(author: Author): AuthorReadDto

    fun toDtoWithBooks(author: Author): AuthorWithBooksReadDto

    @Mapping(target = "id", ignore = true)
    //                           Java, lol ↘
    @Mapping(target = "books", expression = "java(new ArrayList<>())")
    fun toEntity(authorDto: AuthorWriteDto): Author
}
