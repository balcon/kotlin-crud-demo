package com.github.balcon.kotlincruddemo.service

import com.github.balcon.kotlincruddemo.dto.BookDto
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
    private val bookMapper: BookMapper,

    ) {
    fun getById(id: Int): BookDto {
        return bookRepository.findBookWithAuthorById(id)
            ?.let {
                bookMapper.toDtoWithAuthor(it)
            } ?: throw EntityNotFoundException("Book id: $id")
    }

    fun getAll(): List<BookDto> {
        return bookRepository.findAll()
            .map {
                bookMapper.toDtoWithAuthor(it)
            }
    }

    fun getByAuthor(authorId: Int): List<BookDto> {
        return bookRepository.findBookByAuthorId(authorId)
            .map {
                bookMapper.toDto(it)
            }
    }

    @Transactional
    fun create(bookDto: BookDto, authorId: Int): BookDto {
        val author = authorRepository.findAuthorById(authorId)
            ?: throw EntityNotFoundException("Author id: $authorId")
        return bookMapper.toEntity(bookDto, author)
            .let {
                bookMapper.toDtoWithAuthor(bookRepository.save(it))
            }
    }

    @Transactional
    fun update(id: Int, bookDto: BookDto): BookDto {
        bookRepository.findBookWithAuthorById(id)
            ?.let {
                val updatedBook = bookMapper.toEntity(bookDto, it.author)
                return bookMapper.toDtoWithAuthor(bookRepository.save(updatedBook))
            } ?: throw EntityNotFoundException("Book id: $id")
    }

    @Transactional
    fun deleteById(id: Int) {
        bookRepository.findBookById(id)
            ?.let {
                bookRepository.delete(it)
            } ?: throw EntityNotFoundException("Book id: $id")
    }
}
