@file:OptIn(ExperimentalMaterialApi::class)
package com.example.yofu.jobFinder

import com.example.yofu.accountUI.normalFont
import com.example.yofu.accountUI.extraBoldFont
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yofu.R
import androidx.compose.material.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.yofu.R.drawable.business_woman
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import com.example.yofu.R.drawable.casual
import androidx.compose.material.Card
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview



@Composable
fun Homepage() = Surface (
    modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF6F7F9))
        .padding(28.dp),
)
{
    Column(modifier = Modifier
        .background(Color(0xFFF6F7F9)))
    {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column{
                val extraBoldFont = FontFamily(
                    Font(R.font.raleway_bold, FontWeight.Bold),
                )
                Text(
                    text = "Good Jobs",
                    fontFamily = extraBoldFont,
                    style = TextStyle(
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Black,
                        fontStyle = FontStyle.Normal,
                    ),
                )
                Text(
                    text = "Have a nice day!",
                    modifier = Modifier.heightIn(min = 40.dp),
                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                    ),
                )
                Spacer(modifier = Modifier.size(70.dp))
            }
            Image(painter = painterResource(id = R.drawable.business_woman),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(180.dp)
            )
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .clip(RoundedCornerShape(15))
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White
            ),
        )
        {
            Row{
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Icon",
                    tint = Color.Gray
                )
                Spacer(Modifier.width(15.dp))
                Text(text = "Find Your Dream Jobs",
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                    ),
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "New for you",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 40.dp),
            fontFamily = extraBoldFont,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
            ),
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10))
            .background(Color.White)){
            Row(verticalAlignment = Alignment.CenterVertically){
                Column(modifier = Modifier
                    .padding(vertical = 30.dp)
                    .padding(start = 20.dp)) {
                    Text(
                        text = "More new jobs for you!",
                        fontFamily = extraBoldFont,
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                        ),
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .size(height = 40.dp, width = 100.dp)
                            .clip(RoundedCornerShape(10)),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF40A5FE)
                        )
                    )
                    {
                        Text(text = "View", color = Color.White)
                    }
                }
                Image(painter = painterResource(id = casual),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(150.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Recent Jobs",
                fontFamily = extraBoldFont,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                ),
            )
            ClickableText(text = AnnotatedString("See All"), onClick ={},
                style = TextStyle(
                    color = Color(0xFF40A5FE),
                    fontSize = 15.sp,
                    fontFamily = normalFont
                )
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
    }

}