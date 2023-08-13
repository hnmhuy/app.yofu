package com.example.yofu.accountUI

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.yofu.User
import com.example.yofu.UserLogin
import com.example.yofu.accountManage.AuthenticationProcess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CreateAccountState(
    val account: UserLogin,
    val userInfo: User,
    val confirmPassword: String = ""
)

class CreateAccountViewModel(userType: String): ViewModel() {
    private val _state: MutableStateFlow<CreateAccountState> = MutableStateFlow(
        CreateAccountState(
            account = UserLogin(),
            userInfo = User(userType = userType)
        )
    )

    val state: StateFlow<CreateAccountState>
        get() = _state

    fun setFullName(
        newFullName: String
    ) {
        _state.value = _state.value.copy(
            userInfo = _state.value.userInfo.copy(
                fullName = newFullName
            )
        )
    }

    fun setEmail(
        newEmail: String
    ) {
        _state.value = _state.value.copy(
            account = _state.value.account.copy(
                email = newEmail
            )
        )
    }

    fun setPassword(
        newPassword: String
    ) {
        _state.value = _state.value.copy(
            account = _state.value.account.copy(
                password = newPassword
            )
        )
    }

    fun setConfirmPassword(
        newConfirmPassword: String
    ) {
        _state.value = _state.value.copy(
            confirmPassword = newConfirmPassword
        )
    }

    fun setGender (
        newGender: String
    ) {
        _state.value = _state.value.copy(
            userInfo = _state.value.userInfo.copy(
                gender = newGender
            )
        )
    }

    fun checkPassword(): Boolean {
        if (_state.value.account.password == _state.value.confirmPassword) {
            return true
        }
        return false
    }

    fun signup(
        navigateToHomepage: () -> Unit
    ) {
        AuthenticationProcess().signup(
            _state.value.account,
            _state.value.userInfo,
            onComplete = { user, exception ->
                if (exception == null) {
                    Log.d("signup", "Successfully")
                    navigateToHomepage()
                }
                else {
                    Log.d("signup", exception.toString())
                }
            }
        )
    }

}