package ppl.dmitrymix.domclick.interview.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.dao.DataAccessException
import org.springframework.dao.IncorrectResultSizeDataAccessException
import ppl.dmitrymix.domclick.interview.entity.Account
import ppl.dmitrymix.domclick.interview.repository.AccountRepository
import java.math.BigDecimal

@SpringBootTest
@EnableAutoConfiguration(exclude = [LiquibaseAutoConfiguration::class, DataSourceAutoConfiguration::class])
class AccountServiceTest {

    @Autowired
    lateinit var accountService: AccountService

    @MockBean
    lateinit var accountRepository: AccountRepository

    @Test
    fun getAll_should_returnUserAccounts() {
        val expectedAccounts = listOf(Account(1, 1, BigDecimal.ONE))
        `when`(accountRepository.findAllByUserId(1)).thenReturn(expectedAccounts)

        assertThat(accountService.getAll(1)).isEqualTo(expectedAccounts)
    }

    @Test
    fun create_should_createUserAccount() {
        `when`(accountRepository.save(any<Account>())).thenAnswer(returnsFirstArg<Account>())
        val expectedAccount = Account(userId = 1, amount = BigDecimal.ZERO)

        assertThat(accountService.create(1)).isEqualTo(expectedAccount)
        verify(accountRepository).save(expectedAccount)
    }

    @Test
    fun delete_should_dropUserAccount() {
        val accountToDelete = Account(1, 1, BigDecimal.ONE)
        `when`(accountRepository.findByIdAndUserId(1, 1)).thenReturn(accountToDelete)

        accountService.delete(1, 1)

        verify(accountRepository).delete(accountToDelete)
    }

    @Test
    fun transferIn_should_addAmount() {
        `when`(accountRepository.findByIdAndUserId(1, 1)).thenReturn(Account(1, 1, BigDecimal.ONE))
        `when`(accountRepository.save(any<Account>())).thenAnswer(returnsFirstArg<Account>())

        val expectedAccount = Account(1, 1, BigDecimal.valueOf(2))
        val savedAccount = accountService.transferIn(1, 1, BigDecimal.ONE)

        assertThat(savedAccount).isEqualTo(expectedAccount)
        verify(accountRepository).save(expectedAccount)
    }

    @Test
    fun transferIn_should_fail_when_accountNotFound() {
        `when`(accountRepository.findByIdAndUserId(1, 1)).thenThrow(IncorrectResultSizeDataAccessException::class.java)

        assertThrows<DataAccessException> {
            accountService.transferIn(1, 1, BigDecimal.ONE)
        }
    }

    @Test
    fun transferOut_should_subAmount() {
        `when`(accountRepository.findByIdAndUserId(1, 1)).thenReturn(Account(1, 1, BigDecimal.ONE))
        `when`(accountRepository.save(any<Account>())).thenAnswer(returnsFirstArg<Account>())

        val expectedAccount = Account(1, 1, BigDecimal.ZERO)
        val savedAccount = accountService.transferOut(1, 1, BigDecimal.ONE)

        assertThat(savedAccount).isEqualTo(expectedAccount)
        verify(accountRepository).save(expectedAccount)
    }

    @Test
    fun transferOut_should_fail_when_accountNotFound() {
        `when`(accountRepository.findByIdAndUserId(1, 1)).thenThrow(IncorrectResultSizeDataAccessException::class.java)

        assertThrows<DataAccessException> {
            accountService.transferOut(1, 1, BigDecimal.ONE)
        }
    }
}