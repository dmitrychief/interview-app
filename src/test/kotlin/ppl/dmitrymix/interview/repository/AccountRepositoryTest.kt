package ppl.dmitrymix.interview.repository

import com.github.springtestdbunit.annotation.DatabaseSetup
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ppl.dmitrymix.interview.entity.Account
import java.math.BigDecimal

class AccountRepositoryTest : AbstractRepositoryTest() {
    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Test
    @DatabaseSetup("/dbunit/account/account.xml")
    fun findByIdAndByUserId_should_returnAccounts() {
        val account = accountRepository.findByIdAndUserId(1, 1)

        assertThat(account).isEqualTo(Account(1, 1, BigDecimal.ONE))
    }

    @Test
    @DatabaseSetup("/dbunit/account/account.xml")
    fun findAllByUserId_should_returnAccounts() {
        val accounts = accountRepository.findAllByUserId(1)

        assertThat(accounts).containsExactlyInAnyOrder(
                Account(1, 1, BigDecimal.ONE), Account(2, 1, BigDecimal.valueOf(2))
        )
    }
}