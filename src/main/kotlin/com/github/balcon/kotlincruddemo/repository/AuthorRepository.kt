package com.github.balcon.kotlincruddemo.repository

import com.github.balcon.kotlincruddemo.model.Author
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<Author, Int> {
    @EntityGraph(attributePaths = ["books"])
    fun findAuthorWithBooksById(id: Int): Author?

    fun findAuthorById(id: Int): Author?
}
