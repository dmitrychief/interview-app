package ppl.dmitrymix.interview.controller

import org.springframework.web.bind.annotation.*
import ppl.dmitrymix.interview.entity.Account
import ppl.dmitrymix.interview.service.AccountService
import java.math.BigDecimal

@RestController
@RequestMapping("/account")
class AccountController(private val accountService: AccountService) {

    @GetMapping
    fun getAll(@RequestParam("userId") userId: Long): Iterable<Account> {
        return accountService.getAll(userId)
    }

    @PostMapping
    fun create(@RequestParam("userId") userId: Long): Account {
        return accountService.create(userId)
    }

    @DeleteMapping
    fun delete(@RequestParam("id") id: Long, @RequestParam("userId") userId: Long) {
        return accountService.delete(id, userId)
    }

    @PutMapping("/transfer/in")
    fun transferIn(
            @RequestParam("id") id: Long,
            @RequestParam("userId") userId: Long,
            @RequestParam("amount") amount: BigDecimal
    ): Account {
        return accountService.transferIn(id, userId, amount)
    }

    @PutMapping("/transfer/out")
    fun transferOut(
            @RequestParam("id") id: Long,
            @RequestParam("userId") userId: Long,
            @RequestParam("amount") amount: BigDecimal
    ): Account {
        return accountService.transferOut(id, userId, amount)
    }

    @PutMapping("/transfer/to")
    fun transferTo(
            @RequestParam("id") id: Long,
            @RequestParam("userId") userId: Long,
            @RequestParam("amount") amount: BigDecimal,
            @RequestParam("toAccountId") toAccountId: Long,
            @RequestParam("toUserId") toUserId: Long
    ): Account {
        return accountService.transferTo(id, userId, amount, toAccountId, toUserId)
    }
}