package ppl.dmitrymix.domclick.interview.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ExceptionHandler {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgument(e: Exception): String {
        return processException(e)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataAccessException::class)
    fun dataNotFound(e: Exception): String {
        return processException(e)
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(Exception::class)
    fun exception(e: Exception): String {
        return processException(e)
    }

    private fun processException(e: Exception): String {
        logger.error("Exception happened", e)
        return e.message ?: "sorry, unexpected error happened"
    }
}