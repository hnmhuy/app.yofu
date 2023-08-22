package com.example.yofu.employerUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yofu.R
import com.example.yofu.accountUI.BoldFont
import com.example.yofu.accountUI.alert
import com.example.yofu.accountUI.extraBoldFont
import com.example.yofu.accountUI.normalFont
import com.example.yofu.jobfinderUI.NormalFont


@Preview
@Composable
fun DetailedApplicationScreen()
{
    var userName = "Uyen Nhi"
    var email = "fanhi11211@gmail.com"
    var phoneNumber = "0923758923"
    var fileName = "MyCV.pdf"
    var fileSize = "723.2 KB"

    var showDialog by remember { mutableStateOf(false) }
    var reject by remember { mutableStateOf(false) }
    var accept by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .padding(15.dp)
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowCircleLeft,
                        contentDescription = "",
                        tint = Color(0xFF2F4AE3),
                        modifier = Modifier.size(40.dp)
                    )
                }

                Text(
                    text = "Applications",
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontFamily = extraBoldFont,
                    style = TextStyle(
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                    ),
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "",
                    tint = Color.LightGray,
                    modifier = Modifier.size(90.dp)
                )
                Column(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = userName,
                        fontFamily = extraBoldFont,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                        )
                    )

                    Text(
                        text = email,
                        fontFamily = normalFont,
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Italic
                        ),
                        textDecoration = TextDecoration.Underline
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))


            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(modifier = Modifier.padding(start = 5.dp, end = 5.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = 3.dp,
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                            .align(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "",
                            tint = Color(0xFF2F4AE3),
                            modifier = Modifier
                                .size(35.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Contact Information",
                            fontFamily = BoldFont,
                            style = TextStyle(
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                    }
                    Divider(modifier = Modifier.padding(start = 10.dp, end = 10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PersonOutline,
                            contentDescription = "",
                            tint = Color.LightGray,
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = userName,
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
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "",
                            tint = Color.LightGray,
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = phoneNumber,
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
                    ) {
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
                            text = email,
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

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = 3.dp,
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                            .align(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilePresent,
                            contentDescription = "",
                            tint = Color.Blue,
                            modifier = Modifier
                                .size(35.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Candidate's CV/Resume",
                            fontFamily = BoldFont,
                            style = TextStyle(
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Divider(modifier = Modifier.padding(start = 10.dp, end = 10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(painter = painterResource(id = R.drawable.pdf),
                            contentDescription = "pdf",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(40.dp)
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.width(10.dp))
//                        Text(
//                            text = userName,
//                            fontFamily = NormalFont,
//                            style = TextStyle(
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.Normal,
//                                fontStyle = FontStyle.Normal
//                            ),
//                            color = Color.Black,
//                            textAlign = TextAlign.Left,
//                            modifier = Modifier.fillMaxWidth(0.8f)
//                        )

                        Column {
                            Text(
                                text = fileName,
                                fontFamily = NormalFont,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal
                                ),
                                color = Color.Black,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.fillMaxWidth(0.8f)
                            )

                            Text(
                                text = fileSize,
                                fontFamily = NormalFont,
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Normal
                                ),
                                color = Color.Black,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.fillMaxWidth(0.8f)
                            )


                        }
                        IconButton(onClick = { showDialog = true}) {
                            Icon(
                                imageVector = Icons.Filled.FileDownload,
                                contentDescription = "",
                                tint = Color(0xFFFF6E58),
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                    }

                    Divider(modifier = Modifier.padding(start = 5.dp, end = 10.dp))


                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(10.dp).align(Alignment.CenterHorizontally), horizontalArrangement = Arrangement.SpaceBetween)
            {

                Button(
                    onClick = { reject = true },
                    modifier = Modifier
                        .width(150.dp)
                        .padding(4.dp)
                        .heightIn(50.dp),
                    contentPadding = PaddingValues(),
                    enabled = if (accept == true) false else true,
                    colors = ButtonDefaults.buttonColors(Color(0xFFFEEAEA)),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    val boldFont = FontFamily(
                        Font(R.font.raleway_bold, FontWeight.Bold),
                    )
                    Text(
                        text = if(reject == true) "Rejected" else "Reject",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = boldFont,
                        color = if(accept == false) Color(0xFFF85F5F) else Color.White
                    )
                }

                Button(
                    onClick = { accept = true},
                    modifier = Modifier
                        .width(150.dp)
                        .padding(4.dp)
                        .heightIn(50.dp),
                    contentPadding = PaddingValues(),
                    enabled = if (reject == true) false else true,
                    colors = ButtonDefaults.buttonColors(Color(0xFF2F4AE3)),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    val boldFont = FontFamily(
                        Font(R.font.raleway_bold, FontWeight.Bold),
                    )
                    Text(
                        text = if(accept == true) "Accepted" else "Accept",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = boldFont,
                        color = Color.White
                    )
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