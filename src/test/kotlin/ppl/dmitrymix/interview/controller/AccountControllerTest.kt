package ppl.dmitrymix.interview.controller

import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import ppl.dmitrymix.interview.entity.Account
import ppl.dmitrymix.interview.service.AccountService
import java.math.BigDecimal


class AccountControllerTest : AbstractControllerTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var accountService: AccountService

    @Test
    fun getAll_should_returnAllUserAccounts() {
        `when`(accountService.getAll(1)).thenReturn(listOf(Account(1, 1, BigDecimal.ONE)))

        mockMvc.get("/account?userId=1").andExpect {
            status { isOk }
            header { string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) }
            content { json(""" [{"id": 1, "userId": 1, "amount": 1}] """) }
        }
    }

    @Test
    fun create_should_returnCreatedAccount() {
        `when`(accountService.create(1)).thenReturn(Account(1, 1, BigDecimal.ZERO))

        mockMvc.post("/account?userId=1").andExpect {
            status { isOk }
            header { string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) }
            content { json(""" {"id": 1, "userId": 1, "amount": 0} """) }
        }
    }


    @Test
    fun delete_should_dropUserAccount() {
        mockMvc.delete("/account?id=1&userId=2").andExpect {
            status { isOk }
        }

        verify(accountService).delete(1, 2)
    }

    @Test
    fun delete_should_return404_when_accountNotFound() {
        `when`(accountService.delete(1, 2)).thenThrow(EmptyResultDataAccessException("data not found", 1))

        mockMvc.delete("/account?id=1&userId=2").andExpect {
            status { isNotFound }
        }
    }

    @Test
    fun transferIn_should_returnUpdatedAccount() {
        `when`(accountService.transferIn(1, 2, BigDecimal.TEN)).thenReturn(Account(1, 2, BigDecimal.TEN))

        mockMvc.put("/account/transfer/in?id=1&userId=2&amount=10").andExpect {
            status { isOk }
            header { string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) }
            content { json(""" {"id": 1, "userId": 2, "amount": 10} """) }
        }
    }

    @Test
    fun transferIn_should_return400_when_requestIsInvalid() {
        `when`(accountService.transferIn(1, 2, BigDecimal.TEN))
                .thenThrow(IllegalArgumentException::class.java)

        mockMvc.put("/account/transfer/in?id=1&userId=2&amount=10").andExpect {
            status { isBadRequest }
        }
    }

    @Test
    fun transferIn_should_return404_when_accountNotFound() {
        `when`(accountService.transferIn(1, 2, BigDecimal.TEN))
                .thenThrow(EmptyResultDataAccessException::class.java)

        mockMvc.put("/account/transfer/in?id=1&userId=2&amount=10").andExpect {
            status { isNotFound }
        }
    }

    @Test
    fun transferOut_should_returnUpdatedAccount() {
        `when`(accountService.transferOut(1, 2, BigDecimal.TEN)).thenReturn(Account(1, 2, BigDecimal.TEN))

        mockMvc.put("/account/transfer/out?id=1&userId=2&amount=10").andExpect {
            status { isOk }
            header { string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) }
            content { json(""" {"id": 1, "userId": 2, "amount": 10} """) }
        }
    }

    @Test
    fun transferOut_should_return400_when_requestIsInvalid() {
        `when`(accountService.transferOut(1, 2, BigDecimal.TEN))
                .thenThrow(IllegalArgumentException::class.java)

        mockMvc.put("/account/transfer/out?id=1&userId=2&amount=10").andExpect {
            status { isBadRequest }
        }
    }

    @Test
    fun transferOut_should_return404_when_accountNotFound() {
        `when`(accountService.transferOut(1, 2, BigDecimal.TEN))
                .thenThrow(EmptyResultDataAccessException::class.java)

        mockMvc.put("/account/transfer/out?id=1&userId=2&amount=10").andExpect {
            status { isNotFound }
        }
    }

    @Test
    fun transferTo_should_returnUpdatedAccount() {
        `when`(accountService.transferTo(1, 2, BigDecimal.TEN, 3, 4)).thenReturn(Account(1, 2, BigDecimal.TEN))

        mockMvc.put("/account/transfer/to?id=1&userId=2&amount=10&toAccountId=3&toUserId=4").andExpect {
            status { isOk }
            header { string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) }
            content { json(""" {"id": 1, "userId": 2, "amount": 10} """) }
        }
    }

    @Test
    fun transferTo_should_return400_when_requestIsInvalid() {
        `when`(accountService.transferTo(1, 2, BigDecimal.TEN, 3, 4))
                .thenThrow(IllegalArgumentException::class.java)

        mockMvc.put("/account/transfer/to?id=1&userId=2&amount=10&toAccountId=3&toUserId=4").andExpect {
            status { isBadRequest }
        }
    }

    @Test
    fun transferTo_should_return404_when_accountNotFound() {
        `when`(accountService.transferTo(1, 2, BigDecimal.TEN, 3, 4))
                .thenThrow(EmptyResultDataAccessException::class.java)

        mockMvc.put("/account/transfer/to?id=1&userId=2&amount=10&toAccountId=3&toUserId=4").andExpect {
            status { isNotFound }
        }
    }
}