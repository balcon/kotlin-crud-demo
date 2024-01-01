package com.github.balcon.kotlincruddemo.controller

import com.github.balcon.kotlincruddemo.dto.BookDto
import com.github.balcon.kotlincruddemo.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class BookController(
    private val bookService: BookService,
) {
    @GetMapping("/books")
    // Непонятно, зачем собирать ResponseEntity руками,
    // если контроллер может сделать это сам? ¯\_(ツ)_/¯
    fun getAll() =
        ResponseEntity(bookService.getAll(), HttpStatus.OK)

    @GetMapping("/books/{id}")
    fun getById(@PathVariable id: Int) =
        ResponseEntity(bookService.getById(id), HttpStatus.OK)

    @GetMapping("/authors/{authorId}/books")
    fun getByAuthor(@PathVariable authorId: Int) =
        ResponseEntity(bookService.getByAuthor(authorId), HttpStatus.OK)

    @PostMapping("/authors/{authorId}/books")
    fun create(@Validated @RequestBody bookDto: BookDto, @PathVariable authorId: Int) =
        ResponseEntity(bookService.create(bookDto, authorId), HttpStatus.CREATED)

    @PutMapping("/books/{id}")
    fun update(@PathVariable id: Int, @Validated @RequestBody bookDto: BookDto) =
        ResponseEntity(bookService.update(id, bookDto), HttpStatus.NO_CONTENT)

    @DeleteMapping("/books/{id}")
    fun delete(@PathVariable id: Int) =
        ResponseEntity(bookService.deleteById(id), HttpStatus.NO_CONTENT)
}
