package ppl.dmitrymix.domclick.interview.repository

import com.github.springtestdbunit.annotation.DatabaseSetup
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ppl.dmitrymix.domclick.interview.entity.User
import java.util.*

class UserRepositoryTest : AbstractRepositoryTest() {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    @DatabaseSetup("/dbunit/user/user.xml")
    // we actually don't need to test standard repository method, just making sure liquibase migration is ok
    fun findAll_should_returnUsers() {
        assertThat(userRepository.findAll()).containsExactlyInAnyOrder(User(1, "name1"), User(2, "name2"))
    }

    @Test
    @DatabaseSetup("/dbunit/user/user.xml")
    // we actually don't need to test standard repository method, just making sure liquibase migration is ok
    fun findById_should_returnUser() {
        assertThat(userRepository.findById(1)).isEqualTo(Optional.of(User(1, "name1")))
    }
}