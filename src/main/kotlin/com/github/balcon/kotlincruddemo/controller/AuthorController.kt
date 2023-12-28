package com.github.balcon.kotlincruddemo.controller

import com.github.balcon.kotlincruddemo.dto.*
import com.github.balcon.kotlincruddemo.service.AuthorService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.ServletWebRequest

@RestController
@RequestMapping("/authors")
class AuthorController(
    private val authorService: AuthorService,
) {
    @GetMapping
    fun getAll() = authorService.getAll()

    @GetMapping("/{id}")
    fun getAll(@PathVariable id: Int, request: ServletWebRequest) = authorService.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Validated @RequestBody authorDto: AuthorDto) = authorService.create(authorDto)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @Validated @RequestBody authorDto: AuthorDto) =
        authorService.update(id, authorDto)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) = authorService.deleteById(id)
}
