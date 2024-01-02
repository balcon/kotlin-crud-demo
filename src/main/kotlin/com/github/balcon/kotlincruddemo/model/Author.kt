package com.github.balcon.kotlincruddemo.model

import jakarta.persistence.*

@Entity
@NamedEntityGraph(name = "withBooks", attributeNodes = [NamedAttributeNode("books")])
class Author(
    var name: String,

    var country: String?,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    var books: MutableList<Book> = mutableListOf()
) : BaseEntity() {
    override fun toString() = "Author(id = '$id', name='$name', country = '$country'"
}
