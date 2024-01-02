package com.github.balcon.kotlincruddemo

import com.github.balcon.kotlincruddemo.dto.AuthorWriteDto
import com.github.balcon.kotlincruddemo.repository.AuthorRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthorControllerTest : BaseControllerTest() {
    @Autowired
    private lateinit var authorRepository: AuthorRepository

    @Test
    fun `Get all, response json list with 200 status`() {
        mockMvc.perform(get("/authors"))
            .andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    fun `Get by id, response json with 200 status`() {
        mockMvc.perform(get("/authors/1"))
            .andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    fun `Get by id, not exists, response 404 status`() {
        mockMvc.perform(get("/authors/0"))
            .andDo(print())
            .andExpect(status().isNotFound)
    }

    @Test
    fun `Create, response json with 201 status`() {
        val authorDto = AuthorWriteDto(name = "New author")
        mockMvc.perform(
            post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(authorDto))
        )
            .andDo(print())
            .andExpect(status().isCreated)
        authorRepository.flush()
    }

    @Test
    fun `Create, validation error, response 400 status`() {
        val authorDto = AuthorWriteDto(name = "")
        mockMvc.perform(
            post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(authorDto))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `Update, response updated json with 204 status`() {
        val authorDto = AuthorWriteDto(name = "New author")
        mockMvc.perform(
            put("/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(authorDto))
        )
            .andDo(print())
            .andExpect(status().isNoContent)
        authorRepository.flush()
    }

    @Test
    fun `Update, not exists, response 404 status`() {
        val authorDto = AuthorWriteDto(name = "New author")
        mockMvc.perform(
            put("/authors/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(authorDto))
        )
            .andDo(print())
            .andExpect(status().isNotFound)
    }

    @Test
    fun `Update, validation error, response 400 status`() {
        val authorDto = AuthorWriteDto(name = "")
        mockMvc.perform(
            put("/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(authorDto))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `Delete, success, response 204 status`() {
        mockMvc.perform(delete("/authors/1"))
            .andDo(print())
            .andExpect(status().isNoContent)
        authorRepository.flush()
    }

    @Test
    fun `Delete, not exists, response 404 status`() {
        mockMvc.perform(delete("/authors/0"))
            .andDo(print())
            .andExpect(status().isNotFound)
    }
}
