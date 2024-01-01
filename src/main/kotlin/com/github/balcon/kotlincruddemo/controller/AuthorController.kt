package com.github.balcon.kotlincruddemo.controller

import com.github.balcon.kotlincruddemo.dto.*
import com.github.balcon.kotlincruddemo.service.AuthorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.ServletWebRequest

@RestController
@RequestMapping("/authors")
class AuthorController(
    private val authorService: AuthorService,
) {
    @GetMapping
    // Непонятно, зачем собирать ResponseEntity руками
    // если контроллер может сделать это сам? ¯\_(ツ)_/¯
    fun getAll() =
        ResponseEntity(authorService.getAll(), HttpStatus.OK)

    @GetMapping("/{id}")
    fun getAll(@PathVariable id: Int, request: ServletWebRequest) =
        ResponseEntity(authorService.getById(id), HttpStatus.OK)

    @PostMapping
    fun create(@Validated @RequestBody authorDto: AuthorDto) =
        ResponseEntity(authorService.create(authorDto), HttpStatus.CREATED)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @Validated @RequestBody authorDto: AuthorDto) =
        ResponseEntity(authorService.update(id, authorDto), HttpStatus.NO_CONTENT)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) =
        ResponseEntity(authorService.deleteById(id), HttpStatus.NO_CONTENT)
}
