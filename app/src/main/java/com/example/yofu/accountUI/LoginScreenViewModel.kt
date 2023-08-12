package com.example.yofu.accountUI

import android.accounts.Account
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.yofu.UserLogin
import com.example.yofu.accountManage.AuthenticationProcess
import com.google.api.Authentication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class LoginState(
    val email: String = "",
    val password: String = ""
)
class LoginScreenViewModel : ViewModel() {
    val process = AuthenticationProcess()

    private val _state = MutableStateFlow<LoginState>(LoginState())

    val state: StateFlow<LoginState>
        get() = _state

    fun setEmail(
        newEmail: String
    ) {
        _state.value = _state.value.copy(
            email = newEmail
        )
    }

    fun setPassword(newPassword: String) {
        _state.value = _state.value.copy(
            password = newPassword
        )
    }

    fun login(
        navigateToHomepage: () -> Unit
    ) {
        process.login(
            UserLogin(
                email = state.value.email,
                password = state.value.password
            ),
            onComplete = { user, exception ->
                if (exception == null) {
                    Log.d("login", "Successfully")
                    navigateToHomepage()
                }
            }
        )
    }
}