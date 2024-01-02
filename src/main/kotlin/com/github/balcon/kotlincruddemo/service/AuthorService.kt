package com.github.balcon.kotlincruddemo.service

import com.github.balcon.kotlincruddemo.dto.AuthorReadDto
import com.github.balcon.kotlincruddemo.dto.AuthorWithBooksReadDto
import com.github.balcon.kotlincruddemo.dto.AuthorWriteDto
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
    private val authorMapper: AuthorMapper
) {
    fun getById(id: Long): AuthorWithBooksReadDto {
        logger.info { "AuthorService#getById($id)" }
        return authorRepository.findAuthorWithBooksById(id)
            ?.let {
                authorMapper.toDtoWithBooks(it)
            } ?: throw EntityNotFoundException("Author id: $id")
    }

    fun getAll(): List<AuthorReadDto> {
        logger.info { "AuthorService#getAll()" }
        return authorRepository.findAll()
            .map {
                authorMapper.toDto(it)
            }
    }

    @Transactional
    fun create(authorDto: AuthorWriteDto): AuthorReadDto {
        logger.info { "AuthorService#create($authorDto)" }
        with(authorMapper) {
            return toDto(authorRepository.save(toEntity(authorDto)))
        }

    }

    @Transactional
    fun update(id: Long, authorDto: AuthorWriteDto) {
        logger.info { "AuthorService#update($id, $authorDto)" }
        authorRepository.findAuthorById(id)
            ?.apply {
                name = authorDto.name
                country = authorDto.country
            } ?: throw EntityNotFoundException("Author id: $id")
    }

    @Transactional
    fun deleteById(id: Long) {
        logger.info { "AuthorService#delete($id)" }
        authorRepository.findAuthorById(id)
            ?.let {
                authorRepository.delete(it)
            } ?: throw EntityNotFoundException("Author id: $id")
    }
}
