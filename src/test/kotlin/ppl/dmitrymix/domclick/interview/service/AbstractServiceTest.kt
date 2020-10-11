package ppl.dmitrymix.domclick.interview.service

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import ppl.dmitrymix.domclick.interview.repository.AccountRepository
import ppl.dmitrymix.domclick.interview.repository.UserRepository

@SpringBootTest
@ContextConfiguration
class AbstractServiceTest {

    @MockBean
    private lateinit var accountRepository: AccountRepository

    @MockBean
    private lateinit var userRepository: UserRepository

    @TestConfiguration
    @EnableAutoConfiguration(exclude = [
        LiquibaseAutoConfiguration::class, DataSourceAutoConfiguration::class, SecurityAutoConfiguration::class
    ])
    class TestConfig
}