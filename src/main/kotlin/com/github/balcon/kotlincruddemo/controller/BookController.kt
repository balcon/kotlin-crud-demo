package com.github.balcon.kotlincruddemo.controller

import com.github.balcon.kotlincruddemo.dto.BookWriteDto
import com.github.balcon.kotlincruddemo.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(
    private val bookService: BookService,
) {
    @GetMapping
    // Непонятно, зачем собирать ResponseEntity руками
    // если контроллер может сделать это сам? ¯\_(ツ)_/¯
    fun getAll(@RequestParam authorId: Long?) =
        authorId
            ?.let {
                ResponseEntity(bookService.getByAuthor(it), HttpStatus.OK)
            } ?: ResponseEntity(bookService.getAll(), HttpStatus.OK)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) =
        ResponseEntity(bookService.getById(id), HttpStatus.OK)

    @PostMapping
    fun create(@Validated @RequestBody bookDto: BookWriteDto) =
        ResponseEntity(bookService.create(bookDto), HttpStatus.CREATED)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Validated @RequestBody bookDto: BookWriteDto) =
        ResponseEntity(bookService.update(id, bookDto), HttpStatus.NO_CONTENT)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) =
        ResponseEntity(bookService.deleteById(id), HttpStatus.NO_CONTENT)
}
