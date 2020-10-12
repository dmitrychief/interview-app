package ppl.dmitrymix.domclick.interview.controller

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import ppl.dmitrymix.domclick.interview.repository.UserRepository
import ppl.dmitrymix.domclick.interview.service.AccountService
import ppl.dmitrymix.domclick.interview.service.UserService

@WebMvcTest
@ContextConfiguration
abstract class AbstractControllerTest {
    @MockBean
    private lateinit var accountService: AccountService

    @MockBean
    private lateinit var userService: UserService

    @MockBean
    private lateinit var userRepository: UserRepository

    @TestConfiguration
    @EnableAutoConfiguration(exclude = [SecurityAutoConfiguration::class, SecurityFilterAutoConfiguration::class])
    class TestConfig
}