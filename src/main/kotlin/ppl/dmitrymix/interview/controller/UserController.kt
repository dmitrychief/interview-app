package ppl.dmitrymix.interview.controller

import org.springframework.web.bind.annotation.*
import ppl.dmitrymix.interview.entity.User
import ppl.dmitrymix.interview.service.UserService

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAll(): Iterable<User> {
        return userService.getAll()
    }

    @PostMapping
    fun create(@RequestParam("name") name: String): User {
        return userService.create(name)
    }

    @PutMapping
    fun update(@RequestParam("id") id: Long, @RequestParam("name") name: String): User {
        return userService.update(id, name)
    }

    @DeleteMapping
    fun delete(@RequestParam("id") id: Long) {
        return userService.delete(id)
    }
}