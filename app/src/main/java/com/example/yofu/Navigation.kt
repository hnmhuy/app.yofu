package com.example.yofu

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.yofu.accountUI.AboutAccountCompanyScreen
import com.example.yofu.accountUI.ChooseRoleScreen
import com.example.yofu.accountUI.CompanyCreateAccountScreen
import com.example.yofu.accountUI.CreateAccountScreen
import com.example.yofu.accountUI.CreateAccountViewModel
import com.example.yofu.accountUI.LoginScreen
import com.example.yofu.employer.CreateVacancy
import com.example.yofu.employerUI.CompanyScreen
import com.example.yofu.jobFinder.Homepage

enum class Screen {
    // Authentication
    LoginScreen,
    ChooseRoleScreen,
    CreateAccountScreen,
    CompanyCreateAccountScreen,
    AboutAccountCompanyScreen,
    Homepage,
    Search,
    Company,
    CreateVacancy,
    CreatedVacanciesList
}


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = "Authentication"
    ) {
        navigation(
            route = "Authentication",
            startDestination = Screen.LoginScreen.name
        )
        {

            val sharedViewModel = CreateAccountViewModel()

            composable(Screen.LoginScreen.name) {
                LoginScreen(navController = navController)
            }

            composable(Screen.ChooseRoleScreen.name) {
                ChooseRoleScreen(navController = navController)
            }

            composable(Screen.CreateAccountScreen.name) {
                // Set user role
                sharedViewModel.setUserType(
                    newUserType = "JobFinder"
                )
                CreateAccountScreen(
                    navController = navController,
                    createAccountViewModel = sharedViewModel
                )

            }

            composable(Screen.CompanyCreateAccountScreen.name) {
                // Set user role
                sharedViewModel.setUserType(
                    newUserType = "Employer"
                )
                CompanyCreateAccountScreen(
                    navController = navController,
                    viewModel = sharedViewModel
                )
            }

            composable(Screen.AboutAccountCompanyScreen.name) {
                AboutAccountCompanyScreen(
                    navController = navController,
                    viewModel = sharedViewModel
                )
            }
        }
        navigation(
            route = "JobFinder",
            startDestination = Screen.Homepage.name)
        {
            composable(Screen.Homepage.name) {
                Homepage(navController = navController)
            }
            composable(Screen.Search.name) {
                SearchScreen(navController = navController)
            }
        }
        navigation(
            route = "Employer",
            startDestination = Screen.Company.name
        ) {
            composable(Screen.Company.name) {
                CompanyScreen(
                    navController = navController
                )
            }
            composable(Screen.CreateVacancy.name) {
                CreateVacancy(navController = navController)
            }
            composable(Screen.CreatedVacanciesList.name) {
                CreatedVacanciesTemp(navController)
            }
        }
        
    }
}

