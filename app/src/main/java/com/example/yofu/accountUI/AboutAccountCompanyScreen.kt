package com.example.yofu.accountUI

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.yofu.Screen

@Composable
fun AboutAccountCompanyScreen(
    navController: NavController,
    viewModel: CreateAccountViewModel
    )
{
    val context = LocalContext.current.applicationContext

    var isNotified =  remember {
        mutableStateOf(false)
    }

    var message = remember {
        mutableStateOf("")
    }

    val success = remember {
        mutableStateOf(false)
    }

    val isProcessing = remember {
        mutableStateOf(false)
    }

    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F7F9)),
        elevation = 50.dp
    )
    {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(10.dp)
        )
        {
            IconButton( onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowCircleLeft,
                    contentDescription = "",
                    tint = Color(0xFF2F4AE3),
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Start)
                )
            }
            Spacer(modifier = Modifier
                .height(30.dp))
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(150.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            BoldTextComponent(value = "About Your Company")
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent(labelValue = "Company Name",
                previousContent = viewModel.state.collectAsState().value.companyInfomation.name,
                setValue = {
                    viewModel.setCompanyName(it)
            })
            Spacer(modifier = Modifier.height(15.dp))
            TextFieldComponent(labelValue = "Company's address",
                previousContent = viewModel.state.collectAsState().value.companyInfomation.location,
                setValue = {
                    viewModel.setCompanyAddress(it)
                })
            Spacer(modifier = Modifier.height(15.dp))
            TextFieldComponent(labelValue = "Email",
                previousContent = viewModel.state.collectAsState().value.companyInfomation.email,
                setValue = {
                    viewModel.setCompanyEmail(it)
                })
            Spacer(modifier = Modifier.height(15.dp))
            TextFieldComponent(labelValue = "Phone Number",
                previousContent = viewModel.state.collectAsState().value.companyInfomation.phone,
                setValue = {
                    viewModel.setCompanyPhone(it)
                })
            Spacer(modifier = Modifier.height(15.dp))
            TextFieldComponent(labelValue = "Website",
                previousContent = viewModel.state.collectAsState().value.companyInfomation.website,
                setValue = {
                    viewModel.setCompanyWebsite(it)
                })
            Spacer(modifier = Modifier.height(20.dp))
            DescriptionTextFieldComponent(labelValue = "Description",
                previousContent = viewModel.state.collectAsState().value.companyInfomation.description,
                setValue = {
                viewModel.setDiscription(it)
            }) // Description
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponentWithLoading(value = "Create Account", isLoading = isProcessing.value)
            {
                isProcessing.value = true
                var verify = viewModel.verifyForCompany {
                    message.value = it
                }
                if(verify)
                {
                    viewModel.signupForCompany() { it, e ->
                        if(e == null)
                        {
                            isProcessing.value = false
                            isNotified.value = true
                            message.value = "Create account successfully. Please check your email to verify your account"
                            success.value = true
                        }
                        else
                        {
                            isProcessing.value = false
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else
                {
                    isProcessing.value = false
                    Toast.makeText(context, message.value, Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (isNotified.value)
        {
            alert(showDialog = isNotified.value, title = "Notification",  message = message.value) {
                    updateShowDialog -> isNotified.value = updateShowDialog
            }
        }

        if(success.value && !isNotified.value)
        {
            navController.navigate(Screen.LoginScreen.name)
        }
    }
}

@Preview
@Composable
fun AboutAccountCompanyScreenPreview()
{
    AboutAccountCompanyScreen(navController = NavController(LocalContext.current), CreateAccountViewModel())
}