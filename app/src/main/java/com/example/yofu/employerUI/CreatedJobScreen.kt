package com.example.yofu.employerUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.verticalScroll

import androidx.compose.material.Surface

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp

import com.example.yofu.R

import com.example.yofu.accountUI.jobCard
import com.example.yofu.accountUI.jobCard_Employer


@Composable
fun createdJobs()= Surface (
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
            painter = painterResource(id = R.drawable.createdjob),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )

        Column(modifier = Modifier.padding(15.dp)) {
            jobCard_Employer()
            Spacer(modifier = Modifier.height(10.dp))
            jobCard_Employer()
            Spacer(modifier = Modifier.height(10.dp))
            jobCard_Employer()
            Spacer(modifier = Modifier.height(10.dp))
            jobCard_Employer()
            Spacer(modifier = Modifier.height(10.dp))
            jobCard_Employer()
            Spacer(modifier = Modifier.height(10.dp))
            jobCard_Employer()
            Spacer(modifier = Modifier.height(10.dp))
            jobCard_Employer()
            Spacer(modifier = Modifier.height(10.dp))
            jobCard_Employer()
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}