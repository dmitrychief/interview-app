package ppl.dmitrymix.domclick.interview.entity

import org.springframework.data.annotation.Id
import java.math.BigDecimal

data class Account(
        @Id
        var id: Long?,
        var userId: Long,
        var amount: BigDecimal
)