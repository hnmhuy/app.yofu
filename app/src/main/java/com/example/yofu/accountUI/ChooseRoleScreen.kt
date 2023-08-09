package com.example.yofu.accountUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yofu.R


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChooseRoleScreen()
{
    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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
                    modifier = Modifier.size(50.dp)
                )
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()))
        {
            Spacer(modifier = Modifier.height(15.dp))
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
            Spacer(modifier = Modifier.height(15.dp))

            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally)) {
                Card(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .height(200.dp)
                        .width(150.dp)
                        .padding(10.dp),
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = Color.White
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
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .height(200.dp)
                        .width(150.dp)
                        .padding(10.dp),
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = Color.White,
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

            Spacer(modifier = Modifier.height(15.dp))
            ButtonComponent(value = "Continue")
        }
    }
}

@Preview
@Composable
fun ChooseRoleScreenPreview()
{
    ChooseRoleScreen()
}