package ppl.dmitrymix.domclick.interview.repository

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ppl.dmitrymix.domclick.interview.entity.Account

@Repository
interface AccountRepository : CrudRepository<Account, Long> {

    @Query("select * from account where id=:id and user_id=:user_id")
    fun findByIdAndUserId(@Param("id") id: Long, @Param("user_id") userId: Long): Account

    @Query("select * from account where user_id=:user_id")
    fun findAllByUserId(@Param("user_id") userId: Long): List<Account>
}