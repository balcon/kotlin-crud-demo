package com.github.balcon.kotlincruddemo.repository

import com.github.balcon.kotlincruddemo.model.Book
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long> {
    @EntityGraph("withAuthor")
    fun findBookWithAuthorById(id: Long): Book?

    fun findBookById(id: Long): Book?

    fun findBookByAuthorId(authorId: Long): MutableList<Book>

    @EntityGraph("withAuthor")
    override fun findAll(): MutableList<Book>
}
