package ppl.dmitrymix.domclick.interview

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ppl.dmitrymix.domclick.interview.entity.Account
import ppl.dmitrymix.domclick.interview.repository.AccountRepository
import java.math.BigDecimal

@Service
@Transactional
class AccountService(private val accountRepository: AccountRepository) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass.name)

    @Transactional
    fun transferIn(id: Long, userId: Long, amount: BigDecimal): Account {
        logger.info("transferIn started, [id={}, userId={}, amount={}]", id, userId, amount)

        val account = accountRepository.findByIdAndUserId(id, userId)
        account.amount += amount
        accountRepository.save(account)

        logger.info("transferIn finished, [id={}, userId={}, amount={}, result={}]", id, userId, amount, account)

        return account
    }

    @Transactional
    fun transferOut(id: Long, userId: Long, amount: BigDecimal): Account {
        logger.info("transferOut started, [id={}, userId={}, amount={}]", id, userId, amount)

        val account = accountRepository.findByIdAndUserId(id, userId)
        account.amount -= amount
        if (account.amount < BigDecimal.ZERO) {
            throw IllegalArgumentException("overdraft is not possible")
        }
        accountRepository.save(account)

        logger.info("transferOut finished, [id={}, userId={}, amount={}, result={}]", id, userId, amount, account)

        return account
    }


}