package ppl.dmitrymix.domclick.interview.entity

import org.springframework.data.annotation.Id

data class User(
        @Id
        var id: Long? = null,
        var name: String
)