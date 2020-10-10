package ppl.dmitrymix.domclick.interview.utils

import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
open class PostgreSQLIntegrationTest {
    companion object {
        @Container
        val postgreSQLContainer = TestPostgreSQLContainer.sharedPostgreSQLContainer
    }
}