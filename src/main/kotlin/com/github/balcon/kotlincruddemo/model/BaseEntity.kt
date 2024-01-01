package com.github.balcon.kotlincruddemo.model

import jakarta.persistence.*
import org.hibernate.Hibernate

@MappedSuperclass
abstract class BaseEntity(
    @Id
    @SequenceGenerator(
        name = "main_seq", sequenceName = "main_seq",
        initialValue = 100, allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "main_seq")
    var id: Long? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as BaseEntity
        return id == other.id
    }

    override fun hashCode() = Hibernate.getClass(this).hashCode()
}
