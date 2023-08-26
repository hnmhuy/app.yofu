package com.example.yofu.employerUI

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yofu.JobApplication

import com.example.yofu.R
import com.example.yofu.Screen
import com.example.yofu.User
import com.example.yofu.accountManage.UserRepository
import com.example.yofu.accountUI.BoldFont
import com.example.yofu.accountUI.LessBoldTextComponent
import com.example.yofu.accountUI.LoadingScreen
import com.example.yofu.accountUI.NormalTextComponent
import com.example.yofu.accountUI.extraBoldFont
import com.example.yofu.accountUI.jobCardEmployer

import com.example.yofu.accountUI.jobcardEmployerApplications
import com.example.yofu.accountUI.normalFont
import com.example.yofu.jobFinderUI.convertDay
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun user_card(
    content: JobApplication = JobApplication(),
    navController: NavController
)
{
    var userInfo by remember {
        mutableStateOf(User())
    }

    UserRepository().fetch(content.uid) { user, exception ->
        if (exception == null) {
            userInfo = user!!
        } else {
            Log.w("ApplicationList", exception)
        }
    }

    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp,
        modifier = Modifier.padding(10.dp),
        onClick = {
            navController.navigate("${Screen.DetailApplication.name}/${content.aid}")
        },
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
                            text = userInfo.fullName,
                            fontFamily = BoldFont,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,
                            ),
                        )
                        Text(
                            text = if(content.newEmail == "") userInfo.email else content.newEmail,
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun view_jobs(
    navController: NavController,
    applicationsListViewModel: ApplicationsListViewModel = viewModel<ApplicationsListViewModel>()
)= Surface (
    modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF6F7F9))
)
{
    val toastContex = LocalContext.current.applicationContext
    val isOpenDialog = remember { mutableStateOf(false) }

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(2000)
        applicationsListViewModel.loadVacancies()
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)
    Box(modifier = Modifier
        .pullRefresh(state)
        .background(Color(0xFFF6F7F9))
    ) {
        LaunchedEffect(key1 = true)
        {
            applicationsListViewModel.loadVacancies()
            refresh()
        }
        LazyColumn(Modifier.fillMaxSize()) {
            if (!refreshing) {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.allyourapplication),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                items(applicationsListViewModel.vacancyList.value.size) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 0.dp, 10.dp, 0.dp)
                    ) {
                        jobcardEmployerApplications(content = applicationsListViewModel.vacancyList.collectAsState().value[it], navController)
                    }
                }
            }
        }
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
//    Column(
//        modifier = Modifier
//            .background(Color(0xFFF6F7F9))
//            .verticalScroll(rememberScrollState())
//    )
//    {
//        Image(
//            painter = painterResource(id = R.drawable.allyourapplication),
//            contentDescription = "",
//            contentScale = ContentScale.FillWidth,
//            modifier = Modifier
//                .fillMaxWidth()
//        )
//
//        Column(modifier = Modifier.padding(15.dp)) {
//            jobcardEmployerApplications()
//            Spacer(modifier = Modifier.height(10.dp))
//            jobcardEmployerApplications()
//            Spacer(modifier = Modifier.height(10.dp))
//            jobcardEmployerApplications()
//            Spacer(modifier = Modifier.height(10.dp))
//            jobcardEmployerApplications()
//            Spacer(modifier = Modifier.height(10.dp))
//            jobcardEmployerApplications()
//            Spacer(modifier = Modifier.height(10.dp))
//            jobcardEmployerApplications()
//            Spacer(modifier = Modifier.height(10.dp))
//            jobcardEmployerApplications()
//            Spacer(modifier = Modifier.height(10.dp))
//            jobcardEmployerApplications()
//            Spacer(modifier = Modifier.height(10.dp))
//            jobcardEmployerApplications()
//            Spacer(modifier = Modifier.height(10.dp))
//        }
//    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun view_applications(
    navController: NavController,
    vid: String,
    applicationsListViewModel: ApplicationsListViewModel = viewModel<ApplicationsListViewModel>()
)= Surface (
    modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF6F7F9))
)
{
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(isLoading)
    {
        applicationsListViewModel.loadVacancyInfo(vid)
        applicationsListViewModel.loadApplications(vid)
        isLoading = false
    }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        applicationsListViewModel.loadVacancies()
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    if (isLoading) {
        LoadingScreen(isLoading = isLoading)
    }
    else {
        Box(
            modifier = Modifier
                .pullRefresh(state)
                .background(Color(0xFFF6F7F9))
        ) {
            LazyColumn(Modifier.fillMaxSize()) {
                if (!refreshing) {
                    item {
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            elevation = 0.dp,
                        ) {
                            Column(
                            ) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(2.dp))
                                        .height(200.dp)
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                )
                                {

                                    Image(
                                        painter = painterResource(id = R.drawable.logo),
                                        contentDescription = "",
                                        contentScale = ContentScale.Fit
                                    )
                                }
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = applicationsListViewModel.vacancyInfo.collectAsState().value.title,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    fontFamily = BoldFont,
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(
                                        fontSize = 27.sp,
                                        fontWeight = FontWeight.Normal,
                                        fontStyle = FontStyle.Normal,
                                    )

                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                LessBoldTextComponent(value = applicationsListViewModel.vacancyInfo.collectAsState().value.companyName)
                                Spacer(modifier = Modifier.height(25.dp))
                                Divider(
                                    startIndent = 1.dp,
                                    thickness = 0.2.dp,
                                    color = Color.LightGray
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                NormalTextComponent(value = "Posted ${applicationsListViewModel.calDatePosted()}, end in ${convertDay(applicationsListViewModel.vacancyInfo.collectAsState().value.expiredDate)}")
                            }
                        }
                    }
                    items(applicationsListViewModel.applicationsList.value.size) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp, 0.dp, 10.dp, 0.dp)
                        ) {
                            user_card(
                                applicationsListViewModel.applicationsList.collectAsState().value[it],
                                navController
                            )
                        }
                    }
                }
            }
            PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
        }
    }

//    Column(
//        modifier = Modifier
//            .background(Color(0xFFF6F7F9))
//            .verticalScroll(rememberScrollState()),
//        verticalArrangement = Arrangement.Center
//    )
//    {
//        Card(
//            shape = RoundedCornerShape(10.dp),
//            elevation = 0.dp,
//            onClick = { Log.d("Click", "CardExample: Card Click")},
//        ) {
//            Column(
//            ) {
//                Spacer(modifier = Modifier.height(10.dp))
//                Box(
//                    modifier = Modifier
//                        .clip(RoundedCornerShape(2.dp))
//                        .fillMaxWidth(),
//                    contentAlignment = Alignment.Center
//                )
//                {
//
//                    Image(
//                        painter = painterResource(id = R.drawable.logo),
//                        contentDescription = "",
//                    )
//                }
//                Spacer(modifier = Modifier.height(5.dp))
//                Text(
//                    text = "Job name",
//                    modifier = Modifier
//                        .heightIn(min = 55.dp)
//                        .offset(
//                            x = 0.dp,
//                            y = 0.dp
//                        )
//                        .alpha(1.75f)
//                        .fillMaxWidth(),
//                    fontFamily = extraBoldFont,
//                    textAlign = TextAlign.Center,
//                    style = TextStyle(
//                        fontSize = 42.sp,
//                        fontWeight = FontWeight.Normal,
//                        fontStyle = FontStyle.Normal,
//                    )
//
//                )
//                LessBoldTextComponent(value = "Company name")
//                Spacer(modifier = Modifier.height(5.dp))
//                Divider(startIndent = 1.dp, thickness = 0.2.dp, color = Color.LightGray)
//                Spacer(modifier = Modifier.height(5.dp))
//                NormalTextComponent(value = "Posted {n} days ago, end in {Due date}")
//            }
//        }
//        Column(modifier = Modifier.padding(15.dp)) {
//            user_card()
//            Spacer(modifier = Modifier.height(5.dp))
//            user_card()
//            Spacer(modifier = Modifier.height(5.dp))
//            user_card()
//            Spacer(modifier = Modifier.height(5.dp))
//            user_card()
//            Spacer(modifier = Modifier.height(5.dp))
//            user_card()
//            Spacer(modifier = Modifier.height(5.dp))
//            user_card()
//            Spacer(modifier = Modifier.height(5.dp))
//            user_card()
//            Spacer(modifier = Modifier.height(5.dp))
//            user_card()
//            Spacer(modifier = Modifier.height(5.dp))
//        }
    }