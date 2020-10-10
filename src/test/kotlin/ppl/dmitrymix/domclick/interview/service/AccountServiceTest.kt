package ppl.dmitrymix.domclick.interview.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.dao.DataAccessException
import org.springframework.dao.IncorrectResultSizeDataAccessException
import ppl.dmitrymix.domclick.interview.AccountService
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
    fun transferIn_should_addAmount() {
        `when`(accountRepository.findByIdAndUserId(1, 1)).thenReturn(Account(1, 1, BigDecimal.ONE))

        accountService.transferIn(1, 1, BigDecimal.ONE)

        verify(accountRepository).save(Account(1, 1, BigDecimal.valueOf(2)))
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

        accountService.transferOut(1, 1, BigDecimal.ONE)

        verify(accountRepository).save(Account(1, 1, BigDecimal.ZERO))
    }

    @Test
    fun transferOut_should_fail_when_accountNotFound() {
        `when`(accountRepository.findByIdAndUserId(1, 1)).thenThrow(IncorrectResultSizeDataAccessException::class.java)

        assertThrows<DataAccessException> {
            accountService.transferOut(1, 1, BigDecimal.ONE)
        }
    }
}