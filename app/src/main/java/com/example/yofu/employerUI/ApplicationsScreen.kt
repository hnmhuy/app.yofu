package com.example.yofu.employerUI

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Place

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.yofu.accountUI.ExtraBoldTextComponent
import com.example.yofu.accountUI.LessBoldTextComponent
import com.example.yofu.accountUI.NormalTextComponent
import com.example.yofu.accountUI.NotCenterNormalTextComponent
import com.example.yofu.accountUI.extraBoldFont

import com.example.yofu.accountUI.jobCard
import com.example.yofu.accountUI.jobCard_Employer
import com.example.yofu.accountUI.jobCard_Employer_applications
import com.example.yofu.accountUI.jobTag
import com.example.yofu.accountUI.normalFont

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun user_card()
{
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp,
        modifier = Modifier.padding(10.dp),
        onClick = { Log.d("Click", "CardExample: Card Click")},
    ) {
        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(2.dp))
                        .size(50.dp)
                )
                {
                    Image(painter = painterResource(id = R.drawable.userclassic), contentDescription = "")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        val extraBoldFont = FontFamily(
                            Font(R.font.raleway_bold, FontWeight.Bold),
                        )
                        Text(
                            text = "Applicant's name",
                            fontFamily = extraBoldFont,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,
                            ),
                        )
                        Text(
                            text = "email",
                            fontFamily = normalFont,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                        )
                    }
                    IconButton(
                        onClick = { /*TODO*/ },
                    ) {
                        Icon(
                            modifier = Modifier.size(15.dp),
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = "Icon"
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun view_jobs()= Surface (
    modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF6F7F9))
)
{
    val list = listOf(
        "An Giang",
        "Ba Ria – Vung Tau",
        "Bac Giang",
        "Bac Kan",
        "Bac Lieu",
        "Bac Ninh",
        "Ben Tre",
        "Binh Dinh",
        "Binh Duong",
        "Binh Phuoc",
        "Binh Thuan",
        "Ca Mau",
        "Can Tho",
        "Cao Bang",
        "Da Nang",
        "Dak Lak",
        "Dak Nong",
        "Dien Bien",
        "Dong Nai",
        "Dong Thap",
        "Gia Lai",
        "Ha Giang",
        "Ha Nam",
        "Ha Noi",
        "Ha Tinh",
        "Hai Duong",
        "Hai Phong",
        "Hau Giang",
        "Hoa Binh",
        "Hung Yen",
        "Khanh Hoa",
        "Kien Giang",
        "Kon Tum",
        "Lai Chau",
        "Lam Dong",
        "Lang Son",
        "Lao Cai",
        "Long An",
        "Nam Dinh",
        "Nghe An",
        "Ninh Binh",
        "Ninh Thuan",
        "Phu Tho",
        "Phu Yen",
        "Quang Binh",
        "Quang Nam",
        "Quang Ngai",
        "Quang Ninh",
        "Quang Tri",
        "Soc Trang",
        "Son La",
        "Tay Ninh",
        "Thai Binh",
        "Thai Nguyen",
        "Thanh Hoa",
        "Thua Thien Hue",
        "Tien Giang",
        "Ho Chi Minh City",
        "Tra Vinh",
        "Tuyen Quang",
        "Vinh Long",
        "Vinh Phuc",
        "Yen Bai"
    )
    val toastContex = LocalContext.current.applicationContext
    val isOpenDialog = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(Color(0xFFF6F7F9))
            .verticalScroll(rememberScrollState())
    )
    {
        Image(
            painter = painterResource(id = R.drawable.allyourapplication),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )

        Column(modifier = Modifier.padding(15.dp)) {
            jobCard_Employer_applications()
            Spacer(modifier = Modifier.height(10.dp))
            jobCard_Employer_applications()
            Spacer(modifier = Modifier.height(10.dp))
            jobCard_Employer_applications()
            Spacer(modifier = Modifier.height(10.dp))
            jobCard_Employer_applications()
            Spacer(modifier = Modifier.height(10.dp))
            jobCard_Employer_applications()
            Spacer(modifier = Modifier.height(10.dp))
            jobCard_Employer_applications()
            Spacer(modifier = Modifier.height(10.dp))
            jobCard_Employer_applications()
            Spacer(modifier = Modifier.height(10.dp))
            jobCard_Employer_applications()
            Spacer(modifier = Modifier.height(10.dp))
            jobCard_Employer_applications()
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
@Preview
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun view_applications()= Surface (
    modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF6F7F9))
)
{
    Column(
        modifier = Modifier
            .background(Color(0xFFF6F7F9))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    )
    {
        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = 0.dp,
            onClick = { Log.d("Click", "CardExample: Card Click")},
        ) {
            Column(
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(2.dp))
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                )
                {

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "",
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Job name",
                    modifier = Modifier
                        .heightIn(min = 55.dp)
                        .offset(
                            x = 0.dp,
                            y = 0.dp
                        )
                        .alpha(1.75f)
                        .fillMaxWidth(),
                    fontFamily = extraBoldFont,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                    )

                )
                LessBoldTextComponent(value = "Company name")
                Spacer(modifier = Modifier.height(5.dp))
                Divider(startIndent = 1.dp, thickness = 0.2.dp, color = Color.LightGray)
                Spacer(modifier = Modifier.height(5.dp))
                NormalTextComponent(value = "Posted {n} days ago, end in {Due date}")
            }
        }

        Column(modifier = Modifier.padding(15.dp)) {
            user_card()
            Spacer(modifier = Modifier.height(5.dp))
            user_card()
            Spacer(modifier = Modifier.height(5.dp))
            user_card()
            Spacer(modifier = Modifier.height(5.dp))
            user_card()
            Spacer(modifier = Modifier.height(5.dp))
            user_card()
            Spacer(modifier = Modifier.height(5.dp))
            user_card()
            Spacer(modifier = Modifier.height(5.dp))
            user_card()
            Spacer(modifier = Modifier.height(5.dp))
            user_card()
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}