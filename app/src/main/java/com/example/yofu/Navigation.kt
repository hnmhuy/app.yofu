package com.example.yofu

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
import com.example.yofu.employerUI.CompanyProfileScreen
import com.example.yofu.employerUI.CompanyScreen
import com.example.yofu.employerUI.CreatedJobs
import com.example.yofu.jobFinderUI.ApplyScreen
import com.example.yofu.jobFinderUI.Homepage
import com.example.yofu.jobFinderUI.DetailedJobScreen
import com.example.yofu.jobFinderUI.JFApplicationScreen
import com.example.yofu.jobFinderUI.ProfileScreen
import com.example.yofu.jobFinderUI.Search
import kotlinx.coroutines.delay

enum class Screen {
    // Authentication
    LoginScreen,
    ChooseRoleScreen,
    CreateAccountScreen,
    CompanyCreateAccountScreen,
    AboutAccountCompanyScreen,
    // JobFinder
    Homepage,
    Search,
    MyApplication,
    Favorite,
    ProfileIndividual,
    Apply,
    // Employer
    Company,
    CreateVacancy,
    CreatedVacanciesList,
    ApplicationList,
    ProfileCompany,
    DetailVacancy
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
            startDestination = "JobFinderApp"
        )
        {
            composable("JobFinderApp") {
                JobFinderApp(mainNavController = navController)
            }
        }
        navigation(
            route = "Employer",
            startDestination = "EmployerApp"
        ) {
            composable("EmployerApp") {
                EmployerApp(mainNavController = navController)
            }
        }
        
    }
}

private fun navigateInBottomBar(
    navController: NavController,
    screen: String
) {
    navController.navigate(screen) {
        navController.graph.startDestinationRoute?.let {
            popUpTo(it) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun BottomNavigationBarForJobFinder(navController: NavController, isHiden: MutableState<Boolean>)
{
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    AnimatedVisibility(
        visible = isHiden.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        )
    {
        BottomNavigation(
            elevation = 11.dp,
            backgroundColor = Color(0XFF2F4AE3)
        ) {
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home icon",
                    )
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
                label = { Text(text = ("Home"))},
                selected = currentRoute == Screen.Homepage.name,
                onClick = {
                    navigateInBottomBar(navController, Screen.Homepage.name)
                }
            )

            BottomNavigationItem(
                selected = currentRoute == Screen.MyApplication.name,
                onClick = {
                    navigateInBottomBar(navController, Screen.MyApplication.name)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Badge,
                        "Application",
                    )
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
                label = { Text(text = "Application")}
            )

            BottomNavigationItem(
                selected = currentRoute == Screen.Favorite.name,
                onClick = {
                    navigateInBottomBar(navController, Screen.Favorite.name)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        "Favorite",
                    )
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
                label = { Text(text = "Favorite")}
            )

            BottomNavigationItem(
                selected = currentRoute == Screen.ProfileIndividual.name,
                onClick = {
                    navigateInBottomBar(navController, Screen.ProfileIndividual.name)
                },
                icon  = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        "Profile",
                    )
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
                label = { Text(text = "Profile")}
            )
        }
    }
}

@Composable
fun JobFinderApp(navController: NavHostController = rememberNavController(), mainNavController: NavHostController)
{
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    // Hide bottom navigation bar when navigating to detail vacancy screen and apply screen
    when (navController.currentDestination?.route) {
        Screen.DetailVacancy.name, Screen.Apply.name -> {
            bottomBarState.value = false
        }
        else -> {
            bottomBarState.value = true
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBarForJobFinder(navController, bottomBarState)
        },
    ) {
        JobFinderNavGraph(navController = navController, it, mainNavController, bottomBarState)
    }
}

@Composable
fun JobFinderNavGraph(navController: NavHostController, modifier: PaddingValues, mainNavController: NavHostController, bottomBar: MutableState<Boolean>)
{
    NavHost(
        route = "JobFinder",
        navController = navController,
        startDestination = Screen.Homepage.name,
        modifier = Modifier.padding(modifier)
    ){
        composable(Screen.Homepage.name) {
            LaunchedEffect(Unit) {
                bottomBar.value = true
            }
            Homepage(navController = navController)
        }
        composable(Screen.Search.name) {
            LaunchedEffect(Unit) {
                bottomBar.value = true
            }
            Search(navController)
        }
        composable(Screen.MyApplication.name) {
            LaunchedEffect(Unit) {
                bottomBar.value = true
            }
            JFApplicationScreen(navController = navController)
        }
        composable(Screen.Favorite.name) {
            LaunchedEffect(Unit) {
                bottomBar.value = true
            }
            FavoriteScreen(navController = navController)
        }
        composable(Screen.ProfileIndividual.name) {
            LaunchedEffect(Unit) {
                bottomBar.value = true
            }
            ProfileScreen(navController, mainNavController)
        }
        composable(
            "${Screen.DetailVacancy.name}/{vid}",
            arguments = listOf(navArgument("vid") { type = NavType.StringType })
        ) {
            var vid = it.arguments?.getString("vid")
            Log.d("NavigateDetailed", vid.toString())
            if (vid == null) vid = "VID"
            LaunchedEffect(Unit) {
                bottomBar.value = false
                delay(1500)
            }
            DetailedJobScreen(vid = vid, navController = navController)
        }
        composable(
            route = "${Screen.Apply.name}/{vidd}",
            arguments = listOf(navArgument("vidd") { type = NavType.StringType })
        ) {
            val vid = it.arguments?.getString("vidd") ?: "vid"
            Log.d("VID", vid)
            LaunchedEffect(Unit) {
                bottomBar.value = false
            }
            ApplyScreen(navController = navController, vid = vid)
        }
    }
}

@Composable
fun MyApplicationScreen(navController: NavHostController) {
    Text(text = "My Application Temp")
}

@Composable
fun FavoriteScreen(navController: NavHostController) {
    Text(text = "Favorite Temp")
}

@Composable
fun BottomNavigateBarForEmployer(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val fontSize = 10.sp
    BottomNavigation (
        elevation = 11.dp,
        backgroundColor = Color(0XFF2F4AE3)
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Notes,
                    contentDescription = "Home icon",
                )
            },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.Gray,
            label = { Text(text = ("Page"), fontSize = fontSize)},
            selected = true,
            onClick = {
                navigateInBottomBar(navController, Screen.Company.name)
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Checklist,
                    contentDescription = "Home icon",
                )
            },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.Gray,
            label = { Text(text = ("Created jobs"), fontSize = fontSize)},
            selected = true,
            onClick = {
                navigateInBottomBar(navController, Screen.CreatedVacanciesList.name)
            }
        )
        LargeFloatingActionButton(
            onClick = {
                navigateInBottomBar(navController, Screen.CreateVacancy.name)
            },
            modifier = Modifier
                .graphicsLayer {
                    shape = androidx.compose.foundation.shape.CircleShape
                }
                .size(56.dp),
            containerColor = Color.White

        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add new vacancy",
                tint = Color(0XFF2F4AE3),
            )
        }
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Mail,
                    contentDescription = "Home icon",
                )
            },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.Gray,
            label = { Text(text = ("Applications"), fontSize = fontSize)},
            selected = true,
            onClick = {
                navigateInBottomBar(navController, Screen.ApplicationList.name)
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Home icon",
                )
            },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.Gray,
            label = { Text(text = ("Profile"), fontSize = fontSize)},
            selected = true,
            onClick = {
                navigateInBottomBar(navController, Screen.ProfileCompany.name)
            }
        )
    }
}

@Composable
fun EmployerApp(navController: NavHostController = rememberNavController(), mainNavController: NavHostController)
{
    Scaffold(
        bottomBar = {
            BottomNavigateBarForEmployer(navController)
        }
    ) {
        EmployerNavGraph(navController = navController, it, mainNavController)
    }
}

@Composable
fun EmployerNavGraph(navController: NavHostController, it: PaddingValues, mainController: NavHostController){
    NavHost(
        route = "Employer",
        navController = navController,
        startDestination = Screen.Company.name,
        modifier = Modifier.padding(it)
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
            CreatedJobs(navigate = navController)
        }
        composable(Screen.ApplicationList.name) {
            ApplicationListTemp(navController)
        }
        composable(Screen.ProfileCompany.name) {
            CompanyProfileScreen(navController, mainController)
        }
    }
}

@Composable
fun ApplicationListTemp(navController: NavHostController) {
    Text(text = "Application List Temp")
}


