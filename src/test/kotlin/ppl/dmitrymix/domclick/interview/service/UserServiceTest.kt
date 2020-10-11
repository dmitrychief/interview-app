package ppl.dmitrymix.domclick.interview.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import ppl.dmitrymix.domclick.interview.entity.User
import ppl.dmitrymix.domclick.interview.repository.UserRepository
import java.util.*

class UserServiceTest : AbstractServiceTest() {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun getAll_should_returnUsers() {
        val expectedUsers = listOf(User(1, "name"))
        `when`(userRepository.findAll()).thenReturn(expectedUsers)

        assertThat(userService.getAll()).isEqualTo(expectedUsers)
    }

    @Test
    fun create_should_createUser() {
        `when`(userRepository.save(any<User>())).thenAnswer(returnsFirstArg<User>())
        val expectedUser = User(name = "name")

        assertThat(userService.create("name")).isEqualTo(expectedUser)
        verify(userRepository).save(expectedUser)
    }

    @Test
    fun delete_should_dropUserAccount() {
        val userToDelete = User(1, "name")
        `when`(userRepository.findById(1)).thenReturn(Optional.of(userToDelete))

        userService.delete(1)

        verify(userRepository).delete(userToDelete)
    }
}