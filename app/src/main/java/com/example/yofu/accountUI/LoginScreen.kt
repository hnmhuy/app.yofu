package com.example.yofu.accountUI
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yofu.R
import com.example.yofu.Screen
import com.example.yofu.jobFinderUI.NormalFont

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginScreenViewModel = viewModel<LoginScreenViewModel>(),
)
{
    val context = LocalContext.current.applicationContext
    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F7F9)),
        elevation = 50.dp,
        shape = RoundedCornerShape(20.dp)
    )
    {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp))
        {
            Spacer(modifier = Modifier.height(30.dp))
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(200.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            BoldTextComponent(value = "Login to Your Account")
            Spacer(modifier = Modifier.height(25.dp))
            TextFieldComponent(labelValue = "Email address",
                setValue =  { newEmail ->
                    loginViewModel.setEmail(newEmail)
                }
                )
            Spacer(modifier = Modifier.height(13.dp))
            PasswordTextFieldComponent(labelValue = "Password",
                    setValue = {
                        loginViewModel.setPassword(it)
                    }
                )
            Spacer(modifier = Modifier.height(30.dp))
            ButtonComponentWithLoading(
                value = "Sign in",
                isLoading = loginViewModel.isLogin.collectAsState().value,
                callback = {
                     
                    loginViewModel.verityInput(){ message, error, _ ->
                        if(error != null)
                        {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            loginViewModel.login(){message, error, role ->
                                if(error != null) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                } else {
                                    if (role == "Employer") {
                                        navController.navigate("Employer") {
                                            popUpTo("Authentication") {
                                                inclusive = true
                                            }
                                        }
                                    } else {
                                        navController.navigate("JobFinder")
                                        {
                                            popUpTo("Authentication") {
                                                inclusive = true
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            var pop by remember{ mutableStateOf(false) }
            Text(
                modifier = Modifier
                    .clickable { pop = !pop }
                    .fillMaxWidth(),
                text ="Forgot your password",
                fontFamily = NormalFont,
                color = Color(0xFF2F4AE3),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            DividerTextComponent()
            Spacer(modifier = Modifier.height(20.dp))
            ClickableLoginTextComponent(onTextSelected = {
                navController.navigate(Screen.ChooseRoleScreen.name)
            })
            if(pop)
            {
                alert(pop)
                {
                    it-> pop = it
                }
            }
        }

    }
}