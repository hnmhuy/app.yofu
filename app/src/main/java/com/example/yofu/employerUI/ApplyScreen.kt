package com.example.yofu.employerUI

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.Upload
import androidx.compose.runtime.Composable
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
import com.example.yofu.accountUI.TextFieldComponent
import com.example.yofu.accountUI.extraBoldFont
import com.example.yofu.accountUI.normalFont


@Preview
@Composable
fun ApplyScreen()
{

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
            Row(modifier = Modifier.fillMaxWidth()) {
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
                Text(
                    text = "Apply Job",
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 40.dp)
                        .align(Alignment.CenterVertically),
                    fontFamily = extraBoldFont,
                    style = TextStyle(
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                    ),
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier.padding(20.dp).fillMaxHeight(0.9f)
            ) {
                TextFieldComponent(labelValue = "Full Name",
                    setValue = {}) // Chỗ này thêm cái SetValue dô là hết lôi
                Spacer(modifier = Modifier.height(30.dp))
                TextFieldComponent(labelValue = "Email",
                    setValue = {}) // Same
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(150.dp)
                        .border(1.dp, Color.Gray),
                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    shape = RoundedCornerShape(30.dp),
                )
                {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Upload,
                            contentDescription = "",
                            tint = Color.Blue,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(
                            text = "Upload CV/Resume",
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 40.dp),
                            fontFamily = normalFont,
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,
                                color = Color.Gray
                            ),
                            textAlign = TextAlign.Center
                        )
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
                colors = ButtonDefaults.buttonColors(Color.Blue),
                shape = RoundedCornerShape(50.dp)
            ) {
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