package com.github.balcon.kotlincruddemo

import com.github.balcon.kotlincruddemo.dto.AuthorDto
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthorControllerTest : BaseControllerTest() {
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
        // [id = 10] ignored
        val authorDto = AuthorDto(id = 10, name = "New author")
        mockMvc.perform(
            post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(authorDto))
        )
            .andDo(print())
            .andExpect(status().isCreated)
    }

    @Test
    fun `Create, validation error, response 400 status`() {
        val authorDto = AuthorDto(name = "")
        mockMvc.perform(
            post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(authorDto))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `Update by id, response updated json with 204 status`() {
        val authorDto = AuthorDto(name = "New author")
        mockMvc.perform(
            put("/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(authorDto))
        )
            .andDo(print())
            .andExpect(status().isNoContent)
    }

    @Test
    fun `Update by id, not exists, response 404 status`() {
        val authorDto = AuthorDto(name = "New author")
        mockMvc.perform(
            put("/authors/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(authorDto))
        )
            .andDo(print())
            .andExpect(status().isNotFound)
    }

    @Test
    fun `Delete by id, success, response 204 status`() {
        mockMvc.perform(delete("/authors/1"))
            .andDo(print())
            .andExpect(status().isNoContent)
    }

    @Test
    fun `Delete by id, not exists, response 404 status`() {
        mockMvc.perform(delete("/authors/0"))
            .andDo(print())
            .andExpect(status().isNotFound)
    }
}
