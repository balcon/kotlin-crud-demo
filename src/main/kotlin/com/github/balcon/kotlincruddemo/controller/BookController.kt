package com.github.balcon.kotlincruddemo.controller

import com.github.balcon.kotlincruddemo.dto.BookDto
import com.github.balcon.kotlincruddemo.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class BookController(
    private val bookService: BookService,
) {
    @GetMapping("/books")
    fun getAll() = bookService.getAll()

    @GetMapping("/books/{id}")
    fun getById(@PathVariable id: Int) = bookService.getById(id)

    @GetMapping("/authors/{authorId}/books")
    fun getByAuthor(@PathVariable authorId: Int) = bookService.getByAuthor(authorId)

    @PostMapping("/authors/{authorId}/books")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Validated @RequestBody bookDto: BookDto, @PathVariable authorId: Int) =
        bookService.create(bookDto, authorId)

    @PutMapping("/books/{id}")
    fun update(@PathVariable id: Int, @Validated @RequestBody bookDto: BookDto) =
        bookService.update(id, bookDto)

    @DeleteMapping("/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) = bookService.deleteById(id)
}
