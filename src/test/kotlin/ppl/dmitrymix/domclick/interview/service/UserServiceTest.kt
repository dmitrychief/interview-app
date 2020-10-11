package ppl.dmitrymix.domclick.interview.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import ppl.dmitrymix.domclick.interview.entity.Account
import ppl.dmitrymix.domclick.interview.entity.User
import ppl.dmitrymix.domclick.interview.repository.AccountRepository
import ppl.dmitrymix.domclick.interview.repository.UserRepository
import java.math.BigDecimal
import java.util.*

class UserServiceTest : AbstractServiceTest() {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Autowired
    private lateinit var userRepository: UserRepository

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
    fun update_should_updateUser() {
        val user = User(1, "name")
        `when`(userRepository.findById(1)).thenReturn(Optional.of(user))
        `when`(userRepository.save(any<User>())).thenAnswer(returnsFirstArg<User>())

        val expectedUser = user.copy(name= "name2")

        assertThat(userService.update(1, "name2")).isEqualTo(expectedUser)
        verify(userRepository).save(expectedUser)
    }

    @Test
    fun update_should_fail_when_userNotFound() {
        `when`(userRepository.findById(1)).thenReturn(Optional.empty())


        assertThrows<DataAccessException> {
            userService.update(1, "name2")
        }
    }

    @Test
    fun delete_should_dropUserAccount() {
        val userToDelete = User(1, "name")
        `when`(userRepository.findById(1)).thenReturn(Optional.of(userToDelete))

        val accountsToDelete = listOf(Account(1, 1, BigDecimal.ONE))
        `when`(accountRepository.findAllByUserId(1)).thenReturn(accountsToDelete)

        userService.delete(1)

        val inOrder = inOrder(accountRepository, userRepository)
        inOrder.verify(accountRepository).deleteAll(accountsToDelete)
        inOrder.verify(userRepository).delete(userToDelete)
    }

    @Test
    fun delete_should_fail_when_userNotFound() {
        `when`(userRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows<DataAccessException> {
            userService.delete(1)
        }
    }
}