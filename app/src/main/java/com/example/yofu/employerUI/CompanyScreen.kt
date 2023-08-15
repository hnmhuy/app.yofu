package com.example.yofu.employerUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yofu.R
import com.example.yofu.accountUI.alert
import com.example.yofu.accountUI.extraBoldFont
import com.example.yofu.accountUI.normalFont
import com.example.yofu.jobFinderUI.NormalFont

@Preview
@Composable
fun CompanyScreen()
{
    var companyName = "YOFU"
    var aboutCompany = "YOFU is a dynamic IT company dedicated to revolutionizing the job search " +
            "and employment industry. We leverage the latest technological advancements to create " +
            "innovative online platforms and tools that connect job seekers with their ideal " +
            "employment opportunities."
    var companyLocation = "Headquartered in Ho Chi Minh City, Viet Nam, we thrive in a tech hub " +
            "that enables collaboration with industry leaders and experts. Our strategic " +
            "location keeps us at the forefront of innovation in IT and job search."
    var companyNumber = "012413234"
    var companyEmail = "yofu@gmail.com"
    var showDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .padding(start = 20.dp, end = 20.dp, top = 30.dp)
            .verticalScroll(rememberScrollState())
    )
    {
        Column()
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.33f),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Column()
                {
                    Text(
                        text = "Welcome",
                        fontFamily = extraBoldFont,
                        style = TextStyle(
                            fontSize = 27.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                        ),
                    )
                    Text(
                        text = "Have a nice day!",
                        fontFamily = normalFont,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal,
                        ),
                    )
                }
                Image(painter = painterResource(id = R.drawable.bro),
                    contentDescription = "bro",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(150.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(10.dp))
                )
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)){
                Image(painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(80.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(10.dp))
                )
                Column(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ){
                    Row(modifier = Modifier.fillMaxWidth())
                    {
                        Text(
                            text = companyName,
                            fontFamily = extraBoldFont,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,
                            ),
                            color = Color.Blue
                        )
                        IconButton(onClick = { showDialog = true}) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "",
                                tint = Color.Blue,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.CenterVertically),

                                )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))


            }

            section("About company", aboutCompany)
            section(heading = "Location", content = companyLocation)
            Text(
                text = "Contact us",
                fontFamily = extraBoldFont,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                ),
                color = Color.Black,
                modifier = Modifier.padding(top = 1.dp, start = 20.dp, end = 20.dp)
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .fillMaxHeight(0.4f),
                shape = RoundedCornerShape(20.dp),
                elevation = 3.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Filled.Phone,
                            contentDescription = "",
                            tint = Color.LightGray,
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = companyNumber,
                            fontFamily = NormalFont,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Default.MailOutline,
                            contentDescription = "",
                            tint = Color.LightGray,
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = companyEmail,
                            fontFamily = NormalFont,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                    }
                }
            }
        }
    }

    if(showDialog)
    {
        alert(showDialog)
        {
                updateShowDialog -> showDialog = updateShowDialog
        }
    }
}

@Composable
fun section(heading:String, content:String)
{
    Text(
        text = heading,
        fontFamily = extraBoldFont,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
        ),
        color = Color.Black,
        modifier = Modifier.padding(top = 1.dp, start = 20.dp, end = 20.dp)
    )

    Text(
        text = content,
        fontFamily = NormalFont,
        style = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
        ),
        color = Color.Black,
        modifier = Modifier.padding(start = 15.dp, end = 20.dp, bottom = 20.dp),
    )

}