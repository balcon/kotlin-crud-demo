package com.github.balcon.kotlincruddemo.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany

@Entity
class Author(
    var name: String,

    var country: String? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    val books: MutableList<Book> = ArrayList()
) : BaseEntity() {
    override fun toString() = "Author(id = '$id', name='$name', country = '$country'"
}
