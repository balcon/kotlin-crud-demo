package com.github.balcon.kotlincruddemo

import com.github.balcon.kotlincruddemo.dto.BookDto
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class BookControllerTest : BaseControllerTest() {
    @Test
    fun `Get all, response json list with 200 status`() {
        mockMvc.perform(get("/books"))
            .andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    fun `Get by author, response json list with 200 status`() {
        mockMvc.perform(get("/authors/2/books"))
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
        val bookDto = BookDto(title = "New book")
        mockMvc.perform(
            post("/authors/1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(bookDto))
        )
            .andDo(print())
            .andExpect(status().isCreated)
    }

    @Test
    fun `Create, author not exists, 404 status`() {
        val bookDto = BookDto(title = "New book")
        mockMvc.perform(
            post("/authors/0/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(bookDto))
        )
            .andDo(print())
            .andExpect(status().isNotFound)
    }

    @Test
    fun `Create, validation error, response 400 status`() {
        val bookDto = BookDto(title = "")
        mockMvc.perform(
            post("/authors/0/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(bookDto))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `Update by id, response updated json with 200 status`() {
        val bookDto = BookDto(title = "New title")
        mockMvc.perform(
            put("/books/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(bookDto))
        )
            .andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    fun `Update by id, not exists, response 404 status`() {
        val bookDto = BookDto(title = "New title")
        mockMvc.perform(
            put("/books/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(bookDto))
        )
            .andDo(print())
            .andExpect(status().isNotFound)
    }

    @Test
    fun `Delete by id, success, response 204 status`() {
        mockMvc.perform(delete("/books/10"))
            .andDo(print())
            .andExpect(status().isNoContent)
    }

    @Test
    fun `Delete by id, not exists, response 404 status`() {
        mockMvc.perform(delete("/books/0"))
            .andDo(print())
            .andExpect(status().isNotFound)
    }
}
