package com.example.yofu.accountUI

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.EditCalendar
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yofu.R
import com.example.yofu.Screen
import com.example.yofu.employer.DropDown
import com.example.yofu.jobFinderUI.convertDay
import com.google.firebase.Timestamp


@Composable
fun CompanyCreateAccountScreen(
    navController: NavController,
    viewModel: CreateAccountViewModel
    )
{
    val gender = listOf<String>("Male", "Female", "Other")
    val isOpenDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current.applicationContext
    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F7F9)),
        elevation = 50.dp,
        shape = RoundedCornerShape(20.dp)
    )
    {
        Box() {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowCircleLeft,
                    contentDescription = "",
                    tint = Color.Blue,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(10.dp)
                )
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
        )
        {
            Spacer(modifier = Modifier
                .height(30.dp)
                .padding(20.dp))
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(150.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            BoldTextComponent(value = "Create New Account")
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent(labelValue = "Manager's Full Name",
                previousContent = viewModel.state.collectAsState().value.userInfo.fullName,
                setValue = {
                    viewModel.setFullName(it)}
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextFieldComponent(labelValue = "Email",
                previousContent = viewModel.state.collectAsState().value.account.email,
                setValue = {viewModel.setEmail(it)})
            Spacer(modifier = Modifier.height(15.dp))
            TextFieldComponent(labelValue = "Phone",
                setValue = {viewModel.setPhone(it)})
            Spacer(modifier = Modifier.height(15.dp))
            DropDown(
                label = "Manager Gender",
                list = gender,
                previousContent = viewModel.state.collectAsState().value.userInfo.gender,
                setValue = {
                    viewModel.setGender(it)
                }
            )
            Spacer(modifier = Modifier.height(15.dp))
            val selectedDate = remember { mutableStateOf(
                if(viewModel.state.value.userInfo.birthDate == Timestamp(0,0))
                    ""
                else
                    convertDay(viewModel.state.value.userInfo.birthDate)
            ) }
            var dobheading = "Date of birth"
            OutlinedTextField(
                enabled = false,
                readOnly = true,
                textStyle = TextStyle(color = Color.Black),
                value = selectedDate.value,
                onValueChange = {},
                label = {
                    Text(
                        text = dobheading,
                        fontFamily = normalFont,
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                        )
                    )
                },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    cursorColor = Color.Black,
                    disabledTextColor = Color.Transparent,
                    unfocusedBorderColor = Color.LightGray,
                    trailingIconColor = Color.LightGray
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.EditCalendar,
                        modifier = Modifier.clickable {
                            isOpenDialog.value = true
                        },
                        contentDescription = null
                    )
                }
            )
            DatePickerDialog(openDialog = isOpenDialog, disablePast = false)
            {
                selectedDate.value = convertDate(it)
                viewModel.setBirthDate(it)
            }
            Spacer(modifier = Modifier.height(15.dp))
            PasswordTextFieldComponent(labelValue = "Password",
                previousContent = viewModel.state.collectAsState().value.account.password,
                setValue =  {viewModel.setPassword(it)}
            )
            Spacer(modifier = Modifier.height(15.dp))
            PasswordTextFieldComponent(labelValue = "Confirm Password",
                previousContent = viewModel.state.collectAsState().value.confirmPassword,
                setValue = {
                    viewModel.setConfirmPassword(it)
                })
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent(value = "Next",
                callback = {
                    var message = ""
                    var check = viewModel.verifyForIndividual { message = it }
                    if(check)
                    {
                        navController.navigate(Screen.AboutAccountCompanyScreen.name)
                    }
                    else
                    {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}