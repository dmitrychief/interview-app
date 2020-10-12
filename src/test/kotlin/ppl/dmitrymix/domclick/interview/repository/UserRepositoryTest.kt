package ppl.dmitrymix.domclick.interview.repository

import com.github.springtestdbunit.annotation.DatabaseSetup
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ppl.dmitrymix.domclick.interview.entity.User

class UserRepositoryTest : AbstractRepositoryTest() {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    @DatabaseSetup("/dbunit/user/user.xml")
    fun findByName_should_returnUser() {
        assertThat(userRepository.findByName("name1")).isEqualTo(User(1, "name1"))
    }
}