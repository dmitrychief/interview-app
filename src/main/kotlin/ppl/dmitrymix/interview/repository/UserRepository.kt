package ppl.dmitrymix.interview.repository

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ppl.dmitrymix.interview.entity.User

@Repository
interface UserRepository : CrudRepository<User, Long> {
    @Query("select * from users where name=:name")
    fun findByName(@Param("name") name: String): User?
}