package com.github.balcon.kotlincruddemo.service

import com.github.balcon.kotlincruddemo.dto.AuthorDto
import com.github.balcon.kotlincruddemo.dto.mapper.AuthorMapper
import com.github.balcon.kotlincruddemo.exception.ResourceNotExistsException
import com.github.balcon.kotlincruddemo.repository.AuthorRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/** Тест KDoc
 *
 *  Класс обслуживает запросы от [com.github.balcon.kotlincruddemo.web.AuthorController] к [AuthorRepository].
 *
 * @author К. Балыков
 */

private val logger = KotlinLogging.logger { }

@Service
class AuthorService(
    private val repository: AuthorRepository,
    private val mapper: AuthorMapper,
) {
    fun getById(id: Int): AuthorDto {
        logger.info { "AuthorService#getById($id)" }
        return repository.findAuthorWithBooksById(id)
            ?.let {
                mapper.toDtoWithBooks(it)
            } ?: throw ResourceNotExistsException("Author", id)
    }

    fun getAll(): List<AuthorDto> {
        logger.info { "AuthorService#getAll()" }
        return repository.findAll()
            .map { mapper.toDto(it) }
    }

    fun create(authorDto: AuthorDto): AuthorDto {
        logger.info { "AuthorService#create($authorDto)" }
        return mapper.toEntity(authorDto)
            .let {
                mapper.toDto(repository.saveAndFlush(it))
            }
    }

    @Transactional
    fun update(id: Int, authorDto: AuthorDto): AuthorDto {
        logger.info { "AuthorService#update($id, $authorDto)" }
        return repository.findAuthorById(id)
            ?.let {
                it.name = authorDto.name
                it.country = authorDto.country
                mapper.toDto(repository.saveAndFlush(it))
            } ?: throw ResourceNotExistsException("Author", id)
    }

    @Transactional
    fun deleteById(id: Int) {
        logger.info { "AuthorService#delete($id)" }
        repository.findAuthorById(id)
            ?.let {
                repository.delete(it)
            } ?: throw ResourceNotExistsException("Author", id)
        repository.flush()
    }
}
