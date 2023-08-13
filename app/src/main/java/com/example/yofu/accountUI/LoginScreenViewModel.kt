package com.example.yofu.accountUI

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.yofu.UserLogin
import com.example.yofu.accountManage.AuthenticationProcess
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
                email = _state.value.email,
                password = _state.value.password
            ),
            onComplete = { user, exception ->
                if (exception == null) {
                    Log.d("login", "Successfully")
                    navigateToHomepage()
                }
                else {
                    Log.d("login", exception.toString())
                }
            }
        )
    }
}