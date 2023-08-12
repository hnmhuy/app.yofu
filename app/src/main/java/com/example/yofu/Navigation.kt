package com.example.yofu

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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