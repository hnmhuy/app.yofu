package com.example.yofu.jobFinderUI

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yofu.R
import com.example.yofu.accountUI.normalFont
import com.google.android.gms.common.internal.FallbackServiceBroker


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ApplicationCard()
{
    var jobName = "UI/UX Design"
    var companyName = "YOFU Team"

    var  accept = true
    var reject = false

    var tag = ""
    var tagBackgroundColor = Color(0xFFFFFFFF)
    var tagContentColor = Color(0xFFFFFFFF)

    if(accept == false && reject == false)
    {
        tag = "Application Sent"
        tagBackgroundColor = Color(0xFFE9F0FF)
        tagContentColor = Color(0xFF2F4AE3)
    }
    else if(accept == true && reject == false)
    {
        tag = "Application Accepted"
        tagBackgroundColor = Color(0xFFE9FBEF)
        tagContentColor = Color(0xFF08BE75)
    }
    else if (accept == false && reject == true)
    {
        tag = "Application Rejected"
        tagBackgroundColor = Color(0xFFFEEAEA)
        tagContentColor = Color(0xFFF75656)
    }

    Surface(
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 10.dp)
            .height(90.dp)
            .fillMaxWidth(),
    )
    {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            Row(modifier = Modifier.align(Alignment.CenterVertically))
            {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(2.dp))
                        .size(60.dp)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "",
                        modifier = Modifier.size(60.dp)
                        )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = jobName,
                        fontFamily = BoldFont,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal
                        ),
                    )
                    Text(
                        text = companyName,
                        fontFamily = NormalFont,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal
                        ),
                    )

                    Text(
                        text = tag,
                        fontFamily = NormalFont,
                        color = tagContentColor,
                        modifier = Modifier
                            .background(tagBackgroundColor)
                            .padding(3.dp),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal
                        ),
                    )


                }
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "",
                    tint = Color(0xFF2F4AE3),
                    modifier = Modifier.size(30.dp),
                )
            }
        }
    }
}


@Preview
@Composable
fun JFApplicationScreen()
{
    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Color.White)
    )
    {
        Column(modifier = Modifier
            .fillMaxWidth().padding(5.dp),
            verticalArrangement = Arrangement.Center
        )
        {
            Image(
                painter = painterResource(id = R.drawable.applications_screen),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
                    .align(Alignment.CenterHorizontally),
            )
            {
                Column(modifier = Modifier
                    .fillMaxWidth().padding(5.dp)
                    .align(Alignment.CenterHorizontally)
                ) {
                    (1..23).forEach()
                    {
                        ApplicationCard()
                    }
                }
            }
        }
    }
}


