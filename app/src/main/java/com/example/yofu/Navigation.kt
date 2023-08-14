package com.example.yofu

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yofu.accountUI.ChooseRoleScreen
import com.example.yofu.accountUI.CreateAccountScreen
import com.example.yofu.accountUI.LoginScreen
import com.example.yofu.employer.CreateVacancy

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
        addCreateVacancy(navController)
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
    composable("createAccountScreen/{userType}", arguments = listOf(
        navArgument(name = "userType") {
            type = NavType.StringType
        })
    )
    {
        val userType = it.arguments?.getString("userType")
        CreateAccountScreen(navController, userType!!)
    }
}

private fun NavGraphBuilder.addCreateVacancy(navController: NavController) {
    composable("createVacancyScreen") {
        CreateVacancy(navController)
    }
}