package ppl.dmitrymix.interview.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class User(
        @Id
        var id: Long? = null,
        var name: String
)