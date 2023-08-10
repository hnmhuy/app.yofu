package com.example.yofu.accountUI

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yofu.R

@Preview
@Composable
fun CompanyCreateAccountScreen()
{
    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp),
        elevation = 50.dp,
        shape = RoundedCornerShape(20.dp)
    )
    {
        Box {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.ArrowCircleLeft,
                    contentDescription = "",
                    tint = Color.Blue,
                    modifier = Modifier.size(60.dp).padding(10.dp)
                )
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(10.dp)
        )
        {
            Spacer(modifier = Modifier.height(30.dp).padding(20.dp))
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
            TextFieldComponent(labelValue = "Manager's Full Name")
            Spacer(modifier = Modifier.height(15.dp))
            TextFieldComponent(labelValue = "Email")
            Spacer(modifier = Modifier.height(15.dp))
            TextFieldComponent(labelValue = "Manager's Gender")
            Spacer(modifier = Modifier.height(15.dp))
            PasswordTextFieldComponent(labelValue = "Password")
            Spacer(modifier = Modifier.height(15.dp))
            PasswordTextFieldComponent(labelValue = "Confirm Password")
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent(value = "Next")
        }
    }
}