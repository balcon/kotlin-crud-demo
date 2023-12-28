package com.github.balcon.kotlincruddemo.service

import com.github.balcon.kotlincruddemo.dto.AuthorDto
import com.github.balcon.kotlincruddemo.dto.mapper.AuthorMapper
import com.github.balcon.kotlincruddemo.repository.AuthorRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/** Тест KDoc
 *
 *  Класс обслуживает запросы от [com.github.balcon.kotlincruddemo.controller.AuthorController] к [AuthorRepository].
 *
 * @author К. Балыков
 */

private val logger = KotlinLogging.logger { }

@Service
class AuthorService(
    private val authorRepository: AuthorRepository,
    private val authorMapper: AuthorMapper,
) {
    fun getById(id: Int): AuthorDto {
        logger.info { "AuthorService#getById($id)" }
        return authorRepository.findAuthorWithBooksById(id)
            ?.let {
                authorMapper.toDtoWithBooks(it)
            } ?: throw EntityNotFoundException("Author id: $id")
    }

    fun getAll(): List<AuthorDto> {
        logger.info { "AuthorService#getAll()" }
        return authorRepository.findAll()
            .map { authorMapper.toDto(it) }
    }

    @Transactional
    fun create(authorDto: AuthorDto): AuthorDto {
        logger.info { "AuthorService#create($authorDto)" }
        return authorMapper.toEntity(authorDto)
            .let {
                authorMapper.toDto(authorRepository.save(it))
            }
    }

    @Transactional
    fun update(id: Int, authorDto: AuthorDto): AuthorDto {
        logger.info { "AuthorService#update($id, $authorDto)" }
        return authorRepository.findAuthorById(id)
            ?.let {
                it.name = authorDto.name
                it.country = authorDto.country
                authorMapper.toDto(authorRepository.save(it))
            } ?: throw EntityNotFoundException("Author id: $id")
    }

    @Transactional
    fun deleteById(id: Int) {
        logger.info { "AuthorService#delete($id)" }
        authorRepository.findAuthorById(id)
            ?.let {
                authorRepository.delete(it)
            } ?: throw EntityNotFoundException("Author id: $id")
    }
}
