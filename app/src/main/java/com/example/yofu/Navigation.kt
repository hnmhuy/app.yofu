package com.example.yofu

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yofu.accountUI.ChooseRoleScreen
import com.example.yofu.accountUI.CreateAccountScreen
import com.example.yofu.accountUI.LoginScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = "loginScreen"
    ) {
        addLoginScreen(navController)
        addHomepageScreen(navController)
        addChooseRoleScreen(navController)
        addCreateAccountScreen(navController)
    }
}

private fun NavGraphBuilder.addLoginScreen(navController: NavController) {
   composable("loginScreen") {
        LoginScreen(navController)
   }
}

private fun NavGraphBuilder.addHomepageScreen(navController: NavController) {
    composable("homepageScreen") {
        HomepageScreen(navController)
    }
}

private fun NavGraphBuilder.addChooseRoleScreen(navController: NavController) {
    composable("chooseRoleScreen") {
        ChooseRoleScreen(navController)
    }
}

private  fun NavGraphBuilder.addCreateAccountScreen(navController: NavController) {
    composable("createAccountScreen") {
        CreateAccountScreen(navController)
    }
}