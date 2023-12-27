package com.github.balcon.kotlincruddemo.web

import com.github.balcon.kotlincruddemo.dto.*
import com.github.balcon.kotlincruddemo.service.AuthorService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.ServletWebRequest

@RestController
@RequestMapping("/authors")
class AuthorController(
    private val service: AuthorService,
) {
    @GetMapping
    fun getAll() = service.getAll()

    @GetMapping("/{id}")
    fun getAll(@PathVariable id: Int, request: ServletWebRequest) = service.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Validated @RequestBody authorDto: AuthorDto) = service.create(authorDto)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @Validated @RequestBody authorDto: AuthorDto) =
        service.update(id, authorDto)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) = service.deleteById(id)
}
