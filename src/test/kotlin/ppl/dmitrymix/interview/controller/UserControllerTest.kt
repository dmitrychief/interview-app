package ppl.dmitrymix.interview.controller

import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import ppl.dmitrymix.interview.entity.User
import ppl.dmitrymix.interview.service.UserService


class UserControllerTest : AbstractControllerTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun getAll_should_returnAllUsers() {
        `when`(userService.getAll()).thenReturn(listOf(User(1, "name")))

        mockMvc.get("/user").andExpect {
            status { isOk }
            header { string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) }
            content { json(""" [{"id": 1, "name": "name"}] """) }
        }
    }

    @Test
    fun create_should_returnCreatedUser() {
        `when`(userService.create("name")).thenReturn(User(1, "name"))

        mockMvc.post("/user?name=name").andExpect {
            status { isOk }
            header { string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) }
            content { json(""" {"id": 1, "name": "name"} """) }
        }
    }

    @Test
    fun update_should_returnModifiedUser() {
        `when`(userService.update(1, "name2")).thenReturn(User(1, "name2"))

        mockMvc.put("/user?id=1&name=name2").andExpect {
            status { isOk }
            header { string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) }
            content { json(""" {"id": 1, "name": "name2"} """) }
        }
    }

    @Test
    fun update_should_return404_when_userNotFound() {
        `when`(userService.update(1, "name2")).thenThrow(EmptyResultDataAccessException("no data found", 1))

        mockMvc.put("/user?id=1&name=name2").andExpect {
            status { isNotFound }
        }
    }

    @Test
    fun delete_should_dropUser() {
        mockMvc.delete("/user?id=1").andExpect {
            status { isOk }
        }

        verify(userService).delete(1)
    }
}