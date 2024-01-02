package com.github.balcon.kotlincruddemo.service

import com.github.balcon.kotlincruddemo.dto.BookWithAuthorReadDto
import com.github.balcon.kotlincruddemo.dto.BookWriteDto
import com.github.balcon.kotlincruddemo.dto.mapper.BookMapper
import com.github.balcon.kotlincruddemo.repository.AuthorRepository
import com.github.balcon.kotlincruddemo.repository.BookRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository,
    private val bookMapper: BookMapper
) {
    fun getById(id: Long) =
        bookRepository.findBookWithAuthorById(id)
            ?.let {
                bookMapper.toDtoWithAuthor(it)
            } ?: throw EntityNotFoundException("Book id: $id")

    fun getAll() =
        bookRepository.findAll()
            .map {
                bookMapper.toDtoWithAuthor(it)
            }

    fun getByAuthor(authorId: Long) =
        bookRepository.findBookByAuthorId(authorId)
            .map {
                bookMapper.toDto(it)
            }

    @Transactional
    fun create(bookDto: BookWriteDto): BookWithAuthorReadDto =
        bookMapper.toEntity(bookDto, getAuthor(bookDto.authorId))
            .let {
                bookMapper.toDtoWithAuthor(it)
            }

    @Transactional
    fun update(id: Long, bookDto: BookWriteDto) {
        bookRepository.findBookById(id)
            ?.apply {
                title = bookDto.title
                year = bookDto.year
                if (author.id != bookDto.authorId) {
                    author = getAuthor(bookDto.authorId)
                }
            } ?: throw EntityNotFoundException("Book id: $id")
    }

    private fun getAuthor(id: Long) =
        authorRepository.findAuthorById(id)
            ?: throw EntityNotFoundException("Author id: $id")

    @Transactional
    fun deleteById(id: Long) {
        bookRepository.findBookById(id)
            ?.let {
                bookRepository.delete(it)
            } ?: throw EntityNotFoundException("Book id: $id")
    }
}
