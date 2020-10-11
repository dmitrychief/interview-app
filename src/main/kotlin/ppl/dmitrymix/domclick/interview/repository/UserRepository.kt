package ppl.dmitrymix.domclick.interview.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ppl.dmitrymix.domclick.interview.entity.User

@Repository
interface UserRepository : CrudRepository<User, Long>