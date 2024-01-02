package com.github.balcon.kotlincruddemo.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class Book(
    var title: String,

    @Column(name = "writing_year")
    var year: Int?,

    @ManyToOne(fetch = FetchType.LAZY)
    var author: Author
) : BaseEntity() {
    override fun toString() = "Book(id = '$id', title='$title', year = '$year'"
}
