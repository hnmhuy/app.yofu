package com.example.yofu.accountUI


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yofu.R

@Preview
@Composable
fun SplashScreen()
{
    val fontFamily = FontFamily(
        Font(R.font.raleway_black, FontWeight.ExtraBold),
    )
    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        elevation = 50.dp,
        shape = RoundedCornerShape(20.dp)
    )
    {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(150.dp)
                    .fillMaxWidth()
            )
            Text("YOFU",
                fontFamily = fontFamily,
                fontWeight = FontWeight.Black,
                fontSize = 42.sp,
                modifier = Modifier
                    .offset(
                        x = 0.dp,
                        y = 0.dp
                    )
                    .alpha(1.75f)
            )
        }
    }
}