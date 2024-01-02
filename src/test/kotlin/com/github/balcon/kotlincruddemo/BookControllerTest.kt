package com.github.balcon.kotlincruddemo

import com.github.balcon.kotlincruddemo.dto.BookWriteDto
import com.github.balcon.kotlincruddemo.repository.BookRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class BookControllerTest : BaseControllerTest() {
    @Autowired
    private lateinit var bookRepository: BookRepository

    @Test
    fun `Get all, response json list with 200 status`() {
        mockMvc.perform(get("/books"))
            .andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    fun `Get by author, response json list with 200 status`() {
        mockMvc.perform(get("/books?authorId=1"))
            .andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    fun `Get by id, response json with 200 status`() {
        mockMvc.perform(get("/books/10"))
            .andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    fun `Get by id, not exists, response 404 status`() {
        mockMvc.perform(get("/books/0"))
            .andDo(print())
            .andExpect(status().isNotFound)
    }

    @Test
    fun `Create, response json with 201 status`() {
        val bookDto = BookWriteDto(
            title = "New book",
            authorId = 1
        )
        mockMvc.perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(bookDto))
        )
            .andDo(print())
            .andExpect(status().isCreated)
        bookRepository.flush()
    }

    @Test
    fun `Create, author not exists, 404 status`() {
        val bookDto = BookWriteDto(
            title = "New book",
            authorId = 0
        )
        mockMvc.perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(bookDto))
        )
            .andDo(print())
            .andExpect(status().isNotFound)
    }

    @Test
    fun `Create, validation error, response 400 status`() {
        val bookDto = BookWriteDto(
            title = "",
            authorId = 1
        )
        mockMvc.perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(bookDto))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `Update, response updated json with 204 status`() {
        val bookDto = BookWriteDto(
            title = "New title",
            authorId = 1
        )
        mockMvc.perform(
            put("/books/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(bookDto))
        )
            .andDo(print())
            .andExpect(status().isNoContent)
        bookRepository.flush()
    }

    @Test
    fun `Update, not exists, response 404 status`() {
        val bookDto = BookWriteDto(
            title = "New title",
            authorId = 1
        )
        mockMvc.perform(
            put("/books/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(bookDto))
        )
            .andDo(print())
            .andExpect(status().isNotFound)
    }

    @Test
    fun `Update, validation error, response 400 status`() {
        val bookDto = BookWriteDto(
            title = "",
            authorId = 0
        )
        mockMvc.perform(
            put("/books/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(bookDto))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `Update, author not exists, response 404 status`() {
        val bookDto = BookWriteDto(
            title = "New title",
            authorId = 0
        )
        mockMvc.perform(
            put("/books/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(bookDto))
        )
            .andDo(print())
            .andExpect(status().isNotFound)
    }

    @Test
    fun `Delete, success, response 204 status`() {
        mockMvc.perform(delete("/books/10"))
            .andDo(print())
            .andExpect(status().isNoContent)
        bookRepository.flush()
    }

    @Test
    fun `Delete, not exists, response 404 status`() {
        mockMvc.perform(delete("/books/0"))
            .andDo(print())
            .andExpect(status().isNotFound)
    }
}
