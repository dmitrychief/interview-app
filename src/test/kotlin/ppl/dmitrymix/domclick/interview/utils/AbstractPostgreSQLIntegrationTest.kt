package ppl.dmitrymix.domclick.interview.utils

import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@ContextConfiguration
abstract class AbstractPostgreSQLIntegrationTest {
    companion object {
        @JvmStatic
        val singletonPostgreSQLContainer = TestPostgreSQLContainer().apply { start() }

        @DynamicPropertySource
        @JvmStatic
        fun datasourceConfig(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { singletonPostgreSQLContainer.jdbcUrl }
            registry.add("spring.datasource.password") { singletonPostgreSQLContainer.password }
            registry.add("spring.datasource.username") { singletonPostgreSQLContainer.username }
        }
    }
}