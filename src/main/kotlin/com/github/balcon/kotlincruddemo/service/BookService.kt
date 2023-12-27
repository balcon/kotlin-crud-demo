package com.github.balcon.kotlincruddemo.service

import com.github.balcon.kotlincruddemo.dto.BookDto
import com.github.balcon.kotlincruddemo.dto.mapper.BookMapper
import com.github.balcon.kotlincruddemo.exception.ResourceNotExistsException
import com.github.balcon.kotlincruddemo.repository.AuthorRepository
import com.github.balcon.kotlincruddemo.repository.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BookService(
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository,
    private val mapper: BookMapper,

    ) {
    fun getById(id: Int): BookDto {
        return bookRepository.findBookWithAuthorById(id)
            ?.let {
                mapper.toDtoWithAuthor(it)
            } ?: throw ResourceNotExistsException("Book", id)
    }

    fun getAll(): List<BookDto> {
        return bookRepository.findAll()
            .map {
                mapper.toDtoWithAuthor(it)
            }
    }

    fun getByAuthor(authorId: Int): List<BookDto> {
        return bookRepository.findBookByAuthorId(authorId)
            .map {
                mapper.toDto(it)
            }
    }

    @Transactional
    fun create(bookDto: BookDto, authorId: Int): BookDto {
        val author = authorRepository.findAuthorById(authorId)
            ?: throw ResourceNotExistsException("Author", authorId)
        return mapper.toEntity(bookDto, author)
            .let {
                mapper.toDtoWithAuthor(bookRepository.saveAndFlush(it))
            }
    }

    @Transactional
    fun update(id: Int, bookDto: BookDto): BookDto {
        bookRepository.findBookWithAuthorById(id)
            ?.let {
                val updatedBook = mapper.toEntity(bookDto, it.author)
                return mapper.toDtoWithAuthor(bookRepository.saveAndFlush(updatedBook))
            } ?: throw ResourceNotExistsException("Book", id)
    }

    @Transactional
    fun deleteById(id: Int) {
        bookRepository.findBookById(id)
            ?.let {
                bookRepository.delete(it)
            } ?: throw ResourceNotExistsException("Book", id)
        bookRepository.flush()
    }
}
