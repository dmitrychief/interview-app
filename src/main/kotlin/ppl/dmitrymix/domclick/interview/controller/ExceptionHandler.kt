package ppl.dmitrymix.domclick.interview.controller

import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgument(e: Exception): String {
        return getMessage(e)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataAccessException::class)
    fun dataNotFound(e: Exception): String {
        return getMessage(e)
    }

    private fun getMessage(e: Exception): String {
        return e.message ?: "sorry, unexpected error happened"
    }
}