package com.example.yofu.accountUI

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.yofu.Company
import com.example.yofu.User
import com.example.yofu.UserLogin
import com.example.yofu.accountManage.AuthenticationProcess
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CreateAccountState(
    val account: UserLogin,
    val userInfo: User,
    val confirmPassword: String = "",
    val companyInfomation: Company
)

class CreateAccountViewModel(): ViewModel() {
    private val _state: MutableStateFlow<CreateAccountState> = MutableStateFlow(
        CreateAccountState(
            account = UserLogin(),
            userInfo = User(),
            companyInfomation = Company()
        )
    )

    fun setUserType(
        newUserType: String
    ) {
        _state.value = _state.value.copy(
            userInfo = _state.value.userInfo.copy(
                userType = newUserType
            )
        )
    }

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

    fun setBirthDate(newDateDue: Double)
    {
        _state.value = _state.value.copy(
            userInfo = _state.value.userInfo.copy(
                birthDate =  Timestamp((newDateDue/1000).toLong(), ((newDateDue % 1000) * 1000).toInt())
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

    fun signupForIndividual(
        onComplete: (String, Exception?) -> Unit
    ) {
        AuthenticationProcess().signup(
            _state.value.account,
            _state.value.userInfo,
            onComplete = { user, exception ->
                if (exception == null) {
                    val message = "Create account successfully"
                    Log.d("signup", message)
                    onComplete(message, null)
                }
                else {
                    val message = "Create account failed ${exception.toString()}"
                    Log.d("signup", message)
                    onComplete(message, exception)
                }
            }
        )
    }

    fun setCompanyName(newName: String)
    {
        _state.value = _state.value.copy(
            companyInfomation = _state.value.companyInfomation.copy(
                name = newName
            )
        )
    }

    fun setCompanyAddress(newLocation: String)
    {
        _state.value = _state.value.copy(
            companyInfomation = _state.value.companyInfomation.copy(
                location = newLocation
            )
        )
    }

    fun setCompanyEmail(newEmail: String)
    {
        _state.value = _state.value.copy(
            companyInfomation = _state.value.companyInfomation.copy(
                email = newEmail
            )
        )
    }

    fun setCompanyPhone(newPhone: String)
    {
        _state.value = _state.value.copy(
            companyInfomation = _state.value.companyInfomation.copy(
                phone = newPhone
            )
        )
    }

    fun setCompanyWebsite(newWebsite: String)
    {
        _state.value = _state.value.copy(
            companyInfomation = _state.value.companyInfomation.copy(
                website = newWebsite
            )
        )
    }

    fun signupForCompany(
        onComplete: (String, Exception?) -> Unit
    ) {
        AuthenticationProcess().companySignup(
            _state.value.account,
            _state.value.userInfo,
            _state.value.companyInfomation
        ) { e ->
            if (e == null) {
                Log.d("signup", "Create account successfully")
                onComplete("Create account successfully", null)
            }
            else {
                Log.d("signup", e.toString())
                onComplete(e.toString(), e)
            }
        }
    }

}