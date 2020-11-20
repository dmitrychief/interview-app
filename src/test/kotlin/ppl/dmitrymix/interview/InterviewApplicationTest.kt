package ppl.dmitrymix.interview

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ppl.dmitrymix.interview.utils.AbstractPostgreSQLIntegrationTest


@SpringBootTest
class InterviewApplicationTest : AbstractPostgreSQLIntegrationTest() {

    @Test
    fun contextLoads() {
    }
}
