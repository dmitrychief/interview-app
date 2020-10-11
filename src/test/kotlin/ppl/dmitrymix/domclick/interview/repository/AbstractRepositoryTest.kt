package ppl.dmitrymix.domclick.interview.repository

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.support.DirtiesContextTestExecutionListener
import org.springframework.transaction.annotation.Transactional
import ppl.dmitrymix.domclick.interview.utils.AbstractPostgreSQLIntegrationTest

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners(
        DependencyInjectionTestExecutionListener::class,
        DirtiesContextTestExecutionListener::class,
        TransactionDbUnitTestExecutionListener::class
)
@Transactional
@ContextConfiguration
abstract class AbstractRepositoryTest : AbstractPostgreSQLIntegrationTest() {

}