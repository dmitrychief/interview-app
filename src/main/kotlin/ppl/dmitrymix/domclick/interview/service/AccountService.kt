package ppl.dmitrymix.domclick.interview.service

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

    @Transactional(readOnly = true)
    fun getAll(userId: Long): List<Account> {
        logger.info("account getAll started, [userId={}]", userId)

        val accounts = accountRepository.findAllByUserId(userId)

        logger.info("account getAll finished, [userId={}, result={}]", userId, accounts)
        return accounts
    }

    fun create(userId: Long): Account {
        logger.info("account create started, [userId={}]", userId)

        val savedAccount = accountRepository.save(Account(userId = userId, amount = BigDecimal.ZERO))

        logger.info("account create finished, [userId={}, result={}]", userId, savedAccount)
        return savedAccount
    }

    fun delete(id: Long, userId: Long) {
        logger.info("account delete started, [userId={}]", userId)

        val accountToDelete = accountRepository.findByIdAndUserId(id, userId)
        accountRepository.delete(accountToDelete)

        logger.info("account delete finished, [userId={}, result={}]", userId, accountToDelete)
    }

    fun transferIn(id: Long, userId: Long, amount: BigDecimal): Account {
        logger.info("account transferIn started, [id={}, userId={}, amount={}]", id, userId, amount)

        val account = accountRepository.findByIdAndUserId(id, userId)
        account.amount += amount
        val savedAccount = accountRepository.save(account)

        logger.info("account transferIn finished, [id={}, userId={}, amount={}, result={}]",
                id, userId, amount, savedAccount)

        return savedAccount
    }

    fun transferOut(id: Long, userId: Long, amount: BigDecimal): Account {
        logger.info("transferOut started, [id={}, userId={}, amount={}]", id, userId, amount)

        val account = accountRepository.findByIdAndUserId(id, userId)
        account.amount -= amount
        if (account.amount < BigDecimal.ZERO) {
            throw IllegalArgumentException("overdraft is not possible")
        }
        val savedAccount = accountRepository.save(account)

        logger.info("account transferOut finished, [id={}, userId={}, amount={}, result={}]",
                id, userId, amount, savedAccount)

        return savedAccount
    }


}