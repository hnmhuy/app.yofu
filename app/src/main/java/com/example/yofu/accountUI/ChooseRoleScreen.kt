package com.example.yofu.accountUI

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.GroupWork
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yofu.R

const val JOB_FINDER = "JobFinder"
const val EMPLOYER = "Employer"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChooseRoleScreen(
    navController: NavController,
    viewModel: ChooseRoleScreenViewModel = ChooseRoleScreenViewModel()
)
{
    var role = remember {
        mutableStateOf("")
    }

    val jobFinderButton = remember {
        mutableStateOf(false)
    }

    val employerButton = remember {
        mutableStateOf(false)
    }

    Log.d("notify", "choose role screen")

    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        elevation = 50.dp,
        shape = RoundedCornerShape(20.dp)
    )
    {

        Column() {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowCircleLeft,
                    contentDescription = "",
                    tint = Color(0xFF2F4AE3),
                    modifier = Modifier.size(60.dp)
                )
            }

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()))
            {

                Spacer(modifier = Modifier.height(30.dp))
                Image(painter = painterResource(id = R.drawable.choose_role),
                    contentDescription = "role",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(250.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(5.dp))
                BoldTextComponent(value = "Choose Your Role")
                Spacer(modifier = Modifier.height(5.dp))
                NormalTextComponent(value = "Choose whether you ore looking for a job or\n" +
                        "you are an organization/company that needs\n" +
                        "employees")
                Spacer(modifier = Modifier.height(30.dp))

                Row(modifier = Modifier
                    .align(Alignment.CenterHorizontally)) {
                    Card(
                        onClick = {
                            role.value = JOB_FINDER
                            Log.d("role", role.value)
                            jobFinderButton.value = !jobFinderButton.value
                            // Update the button status
                            if (jobFinderButton.value) {
                                employerButton.value = false
                            }
                        },
                        modifier = Modifier
                            .height(250.dp)
                            .width(150.dp)
                            .padding(10.dp),
                        elevation = 4.dp,
                        shape = RoundedCornerShape(8.dp),
                        backgroundColor = if (jobFinderButton.value) Color.LightGray else Color.White
                    )
                    {
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.GroupWork,
                                contentDescription = "",
                                tint = Color.LightGray,
                                modifier = Modifier.size(50.dp)
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            LessBoldTextComponent(value = "Job Finder")
                            NormalTextComponent(value = "I want to find a\n " + "job for me")
                        }
                    }

                    Card(
                        onClick = {
                            role.value = EMPLOYER
                            Log.d("role", role.value)
                            employerButton.value = !employerButton.value
                            // Update the button status
                            if (employerButton.value) {
                                jobFinderButton.value = false
                            }
                        },
                        modifier = Modifier
                            .height(250.dp)
                            .width(150.dp)
                            .padding(10.dp),
                        elevation = 4.dp,
                        shape = RoundedCornerShape(8.dp),
                        backgroundColor = if (employerButton.value) Color.LightGray else Color.White
                    )
                    {
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "",
                                tint = Color.LightGray,
                                modifier = Modifier.size(50.dp)
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            LessBoldTextComponent(value = "Employer")
                            NormalTextComponent(value = "I want to find \n" +
                                    "employees")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))
                ButtonComponent(value = "Continue",
                    callback = {
                        viewModel.setRole(role.value)
                        viewModel.continuteToCreateAccount(navController)
                    })
            }
        }
    }
}

@Preview
@Composable
fun ChooseRoleScreenPreview() {
    ChooseRoleScreen(navController = NavController(LocalContext.current))
}