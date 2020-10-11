package ppl.dmitrymix.domclick.interview.service

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import ppl.dmitrymix.domclick.interview.repository.AccountRepository
import ppl.dmitrymix.domclick.interview.repository.UserRepository

@SpringBootTest
@ContextConfiguration
abstract class AbstractServiceTest {

    @MockBean
    private lateinit var accountRepository: AccountRepository

    @MockBean
    private lateinit var userRepository: UserRepository

    // for the simplicity we start whole spring context, excluding things like db that are not needed for service tests.
    // todo: remove autoconfiguration, introduce manual componentScan to configure services only
    @TestConfiguration
    @EnableAutoConfiguration(exclude = [
        LiquibaseAutoConfiguration::class, DataSourceAutoConfiguration::class, SecurityAutoConfiguration::class,
        UserDetailsServiceAutoConfiguration::class
    ])
    class TestConfig
}