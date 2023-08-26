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
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import com.example.yofu.JobApplication
import com.example.yofu.R
import com.example.yofu.Screen
import com.example.yofu.Vacancy
import com.example.yofu.accountUI.jobCardEmployer
import com.example.yofu.accountUI.normalFont
import com.example.yofu.jobVacancyManage.VacancyRepository
import com.google.android.gms.common.internal.FallbackServiceBroker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ApplicationCard(data: JobApplication, navController: NavController)
{
    val jobData : MutableStateFlow<Vacancy> = MutableStateFlow(Vacancy())

    VacancyRepository().fetch(data.vid) { vacancy, e ->
        if (e == null) {
            if (vacancy != null)
            {
                jobData.value = vacancy
            }
            else
            {
                Log.d("DetailedJobScreen", "Vacancy is null")
            }
        }
    }

    var tag = ""
    var tagBackgroundColor = Color(0xFFFFFFFF)
    var tagContentColor = Color(0xFFFFFFFF)

    if(data.status == null)
    {
        tag = "Application Sent"
        tagBackgroundColor = Color(0xFFE9F0FF)
        tagContentColor = Color(0xFF2F4AE3)
    }
    else if(data.status == true)
    {
        tag = "Application Accepted"
        tagBackgroundColor = Color(0xFFE9FBEF)
        tagContentColor = Color(0xFF08BE75)
    }
    else if (data.status == false)
    {
        tag = "Application Rejected"
        tagBackgroundColor = Color(0xFFFEEAEA)
        tagContentColor = Color(0xFFF75656)
    }

    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .height(100.dp)
            .background(Color(0xFFF6F7F9))
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
        onClick = {}
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
                        text = jobData.collectAsState().value.title,
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
                        text = jobData.collectAsState().value.companyName,
                        fontFamily = NormalFont,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal
                        ),
                    )
                    Spacer(modifier = Modifier.height(5.dp))
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
            IconButton(onClick = {
                navController.navigate("${Screen.DetailVacancy.name}/${jobData.value.vid}")
            }) {
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun JFApplicationScreen(
    navController: NavController,
    jfApplicationViewModel: JFApplicationViewModel = JFApplicationViewModel()
)
{
    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Color.White)
    )
    {
        val refreshScope = rememberCoroutineScope()
        var refreshing by remember { mutableStateOf(false) }

        fun refresh() = refreshScope.launch {
            refreshing = true
            delay(1000)
            jfApplicationViewModel.getApplicationList()
            refreshing = false
        }
        val state = rememberPullRefreshState(refreshing, ::refresh)
        LaunchedEffect(key1 = true)
        {
            jfApplicationViewModel.getApplicationList()
            refresh()
        }

        Box(modifier = Modifier
            .pullRefresh(state)
            .background(Color(0xFFF6F7F9))
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(Modifier.fillMaxSize()) {
                // Checking if data is loaded
                if (!refreshing) {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.applications_screen),
                            contentDescription = "",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    if(jfApplicationViewModel.applicationList.value.isEmpty())
                    {
                        // You have no application
                        item {
                            Column(modifier = Modifier
                                .fillMaxHeight()
                                .padding(5.dp),
                                verticalArrangement = Arrangement.Center
                            )
                            {
                                Spacer(modifier = Modifier.height(250.dp))
                                Text(
                                    text = "You have not applied any jobs.",
                                    fontFamily = BoldFont,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(),
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Normal
                                    ),
                                )
                            }
                        }
                    }
                    else
                    {
                        items(jfApplicationViewModel.applicationList.value.size) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp, 0.dp, 15.dp, 0.dp)
                            ) {
                                ApplicationCard(jfApplicationViewModel.applicationList.collectAsState().value[it], navController)
                            }
                        }
                    }
                }
            }
            PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
        }
    }
}


