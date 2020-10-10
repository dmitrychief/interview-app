package ppl.dmitrymix.domclick.interview.repository

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.support.DirtiesContextTestExecutionListener
import org.springframework.transaction.annotation.Transactional
import ppl.dmitrymix.domclick.interview.entity.Account
import ppl.dmitrymix.domclick.interview.utils.PostgreSQLIntegrationTest

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners(
        DependencyInjectionTestExecutionListener::class,
        DirtiesContextTestExecutionListener::class,
        TransactionDbUnitTestExecutionListener::class
)
@Transactional
class AccountRepositoryTest : PostgreSQLIntegrationTest() {
    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Test
    @DatabaseSetup("/dbunit/account/account.xml")
    fun getByIdAndByUserId_should_returnAccounts() {
        val account = accountRepository.findByIdAndUserId(1, 1)

        assertThat(account).isEqualTo(Account(1, 1, 1))
    }
}