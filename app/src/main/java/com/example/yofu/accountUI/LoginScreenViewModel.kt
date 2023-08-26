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

    val isLogin = MutableStateFlow(false)

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

    fun verityInput(
        onComplete: (String, Exception?, String?) -> Unit
    ) {
        if (_state.value.email.isEmpty() || _state.value.password.isEmpty()) {
            onComplete("Please fill in all the fields", Exception("Please fill in all the fields"), null)
        }
        else {
            login(onComplete)
        }
    }

    fun login(
        oncomplete: (String, Exception?, String?) -> Unit
    ) {
        isLogin.value = true
        process.login(
            UserLogin(
                email = _state.value.email,
                password = _state.value.password
            ),
            onComplete = { user, exception ->
                if (exception == null) {
                    Log.d("login", "Login successfully")
                    oncomplete("Welcome back ${user?.fullName}", null, user?.userType)
                }
                else {
                    isLogin.value = false
                    Log.d("login", exception.message.toString())
                    oncomplete("Login failed ${exception.message.toString()}", exception, null)
                }
            }
        )
    }
}