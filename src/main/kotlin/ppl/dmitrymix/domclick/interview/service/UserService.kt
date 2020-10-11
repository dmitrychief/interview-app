package ppl.dmitrymix.domclick.interview.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ppl.dmitrymix.domclick.interview.entity.User
import ppl.dmitrymix.domclick.interview.repository.AccountRepository
import ppl.dmitrymix.domclick.interview.repository.UserRepository

@Service
@Transactional
class UserService(private val userRepository: UserRepository, private val accountRepository: AccountRepository) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass.name)

    @Transactional(readOnly = true)
    fun getAll(): Iterable<User> {
        logger.info("user getAll started")

        val users = userRepository.findAll()

        logger.info("user getAll finished, [result={}]", users)
        return users
    }

    fun create(name: String): User {
        logger.info("user create started, [name={}]", name)

        val savedUser = userRepository.save(User(name = name))

        logger.info("user create finished, [name={}, result={}]", name, savedUser)
        return savedUser
    }

    fun update(id: Long, name: String): User {
        logger.info("user update started, [id={}, name={}]", id, name)

        val user = userRepository.findById(id)
                .orElseThrow { EmptyResultDataAccessException("user not found, id=$id", 1) }

        user.name = name
        val savedUser = userRepository.save(user)

        logger.info("user update finished, [id={}, name={}]", id, name, savedUser)
        return savedUser
    }

    fun delete(id: Long) {
        logger.info("user delete started, [id={}]", id)

        val userToDelete = userRepository.findById(id)
                .orElseThrow { EmptyResultDataAccessException("user not found, id=$id", 1) }

        val userAccounts = accountRepository.findAllByUserId(id)
        accountRepository.deleteAll(userAccounts)
        userRepository.delete(userToDelete)

        logger.info("user delete finished, [id={}, result={}]", id, userToDelete)
    }
}