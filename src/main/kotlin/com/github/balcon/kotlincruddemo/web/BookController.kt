package com.github.balcon.kotlincruddemo.web

import com.github.balcon.kotlincruddemo.dto.BookDto
import com.github.balcon.kotlincruddemo.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class BookController(
    private val service: BookService,
) {
    @GetMapping("/books")
    fun getAll() = service.getAll()

    @GetMapping("/books/{id}")
    fun getById(@PathVariable id: Int) = service.getById(id)

    @GetMapping("/authors/{authorId}/books")
    fun getByAuthor(@PathVariable authorId: Int) = service.getByAuthor(authorId)


    @PostMapping("/authors/{authorId}/books")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Validated @RequestBody bookDto: BookDto, @PathVariable authorId: Int) =
        service.create(bookDto, authorId)

    @PutMapping("/books/{id}")
    fun update(@PathVariable id: Int, @Validated @RequestBody bookDto: BookDto) =
        service.update(id, bookDto)


    @DeleteMapping("/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) = service.deleteById(id)
}
