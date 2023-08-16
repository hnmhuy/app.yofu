package com.example.yofu.accountUI

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.yofu.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChooseRoleScreenViewModel: ViewModel() {
    private val _role = MutableStateFlow("")
    val role: StateFlow<String>
        get() = _role

    fun setRole(newRole: String)
    {
        _role.value = newRole
    }

    fun continuteToCreateAccount(navController: NavController)
    {
        if (_role.value == JOB_FINDER)
        {
            navController.navigate(Screen.CreateAccountScreen.name)
        }
        else if (_role.value == EMPLOYER)
        {
            navController.navigate(Screen.CompanyCreateAccountScreen.name)
        }
    }

    fun backToLogin(navController: NavController)
    {
        navController.navigate(Screen.LoginScreen.name)
    }

}