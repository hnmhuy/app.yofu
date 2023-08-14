package com.example.yofu.employerUI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Preview
@Composable
fun DetailedJobScreen()
{
    val mediumFont = FontFamily(
        Font(R.font.raleway_medium, FontWeight.Medium),
    )
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .padding(10.dp),
        elevation = 50.dp
    )
    {
        Column(modifier = Modifier.fillMaxWidth())
        {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Box(modifier = Modifier.padding(10.dp)) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowCircleLeft,
                            contentDescription = "",
                            tint = Color.Blue,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
                var isTinted by remember { mutableStateOf(false) }
                Box(modifier = Modifier
                    .padding(10.dp)) {
                    IconButton(onClick = { isTinted = !isTinted }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "",
                            tint = if(isTinted) Color.Red else Color.LightGray,
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(0.45f)
                    .padding(20.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(10.dp),
                elevation = 4.dp
            ) {
                Column {
                    // Hỏi lại mọi người mấy tấm hình display làm shaoooo
                }
            }
            Surface(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
            ){
                Column {
                    val tabs = listOf("Job description", "Benefits", "Company Info")
                    var selectedTabIndex by remember { mutableStateOf(0) }
                    TabRow(
                        modifier = Modifier.padding(13.dp),
                        selectedTabIndex = selectedTabIndex,
                        backgroundColor = Color.White,
                        contentColor = Color.Blue,
                    )
                    {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = index == selectedTabIndex,
                                onClick = { selectedTabIndex = index }
                            ) {
                                Text(
                                    text = title,
                                    modifier = Modifier
                                        .padding(10.dp),
                                    fontWeight = if (index == selectedTabIndex) FontWeight.Bold else FontWeight.Normal,
                                    fontFamily = mediumFont,
                                    color = Color.Black,
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal,
                                        fontStyle = FontStyle.Normal
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    when (selectedTabIndex) {
                        0 -> TabContent("Jod Description","InnovateTech Solutions is seeking a talented and motivated Digital Marketing Specialist to join our dynamic marketing team. As a Digital Marketing Specialist, you will play a pivotal role in developing and implementing digital marketing strategies that drive brand awareness, engagement, and lead generation for our innovative tech solutions. This is an exciting opportunity to contribute to a growing company at the forefront of technological advancements.a growing company at the forefront of technological advancements.a growing company at the forefront of technological advancements.a growing company at the forefront of technological advancements.")
                        1 -> TabContent("Benefits","Bachelor's degree in Marketing, Business, or a related field. Relevant certifications (e.g., Google Ads, HubSpot, etc.) are a plus.\n" +
                                "Proven experience (2+ years) in digital marketing, including hands-on experience with social media management, email marketing, SEO, and SEM.\n" +
                                "Strong analytical skills with the ability to interpret data, draw conclusions, and make data-driven recommendations.")
                        2 -> TabContent("Company Information","To apply, please submit your resume, a cover letter outlining your relevant experience, and examples of successful digital marketing campaigns you have managed. Please also include your salary expectations and earliest availability. Send your application to careers@innovatetech.com with the subject line: \"Digital Marketing Specialist Application - [Your Name]\".")
                    }
                }

            }

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(50.dp)
                    .padding(horizontal = 10.dp),
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                shape = RoundedCornerShape(50.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(42.dp)
                        .background(
                            color = Color.Blue,
                            shape = RoundedCornerShape(50.dp)
                        ),
                    contentAlignment = Alignment.Center
                )
                {
                    val boldFont = FontFamily(
                        Font(R.font.raleway_bold, FontWeight.Bold),
                    )
                    Text(
                        text = "Apply",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = boldFont,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun TabContent(heading:String, content:String)
{
    val normalFont = FontFamily(
        Font(R.font.raleway_regular, FontWeight.Thin),
    )
    val boldFont = FontFamily(
        Font(R.font.raleway_bold, FontWeight.Bold),
    )
    Surface(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Text(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                text = heading,
                fontFamily = boldFont,
                color = Color.Black,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                ),
            )
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = content,
                fontFamily = normalFont,
                color = Color.Black,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                ),
            )
        }

    }
}