package ppl.dmitrymix.domclick.interview.entity

import org.springframework.data.annotation.Id

data class Account(
        @Id
        var id: Long?,
        var userId: Long,
        var amount: Long
)