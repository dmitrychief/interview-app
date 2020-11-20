package ppl.dmitrymix.interview.utils

import org.testcontainers.containers.PostgreSQLContainer

class TestPostgreSQLContainer : PostgreSQLContainer<TestPostgreSQLContainer>("postgres:11.4-alpine") {
/*
    companion object {
        @JvmStatic
        val sharedPostgreSQLContainer by lazy { TestPostgreSQLContainer().withReuse(true) }
    }

    override fun start() {
        super.start()
        System.setProperty("spring.datasource.url", sharedPostgreSQLContainer.jdbcUrl)
        System.setProperty("spring.datasource.username", sharedPostgreSQLContainer.username)
        System.setProperty("spring.datasource.password", sharedPostgreSQLContainer.password)
    }*/
}