package com.github.balcon.kotlincruddemo.model

import jakarta.persistence.*

@Entity
@NamedEntityGraph(name = "withAuthor", attributeNodes = [NamedAttributeNode("author")])
class Book(
    var title: String,

    @Column(name = "writing_year")
    var year: Int?,

    @ManyToOne(fetch = FetchType.LAZY)
    var author: Author
) : BaseEntity() {
    override fun toString() = "Book(id = '$id', title='$title', year = '$year'"
}
