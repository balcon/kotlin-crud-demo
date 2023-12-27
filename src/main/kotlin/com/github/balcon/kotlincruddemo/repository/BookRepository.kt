package com.github.balcon.kotlincruddemo.repository

import com.github.balcon.kotlincruddemo.model.Book
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Int> {
    @EntityGraph(attributePaths = ["author"])
    fun findBookWithAuthorById(id: Int): Book?

    fun findBookById(id: Int): Book?

    fun findBookByAuthorId(authorId: Int): MutableList<Book>

    @EntityGraph(attributePaths = ["author"])
    override fun findAll(): MutableList<Book>
}
