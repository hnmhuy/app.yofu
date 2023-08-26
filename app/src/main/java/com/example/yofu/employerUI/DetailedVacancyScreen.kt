package com.example.yofu.employerUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yofu.R
import com.example.yofu.Screen
import com.example.yofu.accountUI.LoadingScreen
import com.example.yofu.accountUI.alert
import com.example.yofu.accountUI.jobTag
import com.example.yofu.jobFinderUI.BoldFont
import com.example.yofu.jobFinderUI.DetailedJobViewModel
import com.example.yofu.jobFinderUI.NormalFont
import com.example.yofu.jobFinderUI.mediumFont
import com.example.yofu.jobVacancyManage.ApplyRepository
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun DetailedVacancyScreen(
    vid: String,
    navController: NavController,
    detailedJobScreenViewModel: DetailedJobViewModel = viewModel<DetailedJobViewModel>()
)
{
    var isLoading by remember {
        mutableStateOf(true)
    }
    var isApplied by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isLoading){
        detailedJobScreenViewModel.setVacancyId(vid)
        detailedJobScreenViewModel.loadVacancy()
        ApplyRepository().isApplied(vid) {
            isApplied = it
        }
        delay(1000)
        isLoading = false
    }

    val jobContent by detailedJobScreenViewModel.state.collectAsState()
    val companyContent by detailedJobScreenViewModel.company.collectAsState()
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFFF6F7F9)),
        elevation = 50.dp
    )
    {
        if(isLoading)
        {
            LoadingScreen(isLoading = isLoading)
        }
        else
        {
            Column(modifier = Modifier.fillMaxWidth().padding(10.dp))
            {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Box(modifier = Modifier.padding(10.dp)) {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowCircleLeft,
                                contentDescription = "",
                                tint = Color(0xFF2F4AE3),
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
                    if(isTinted)
                    {
                        alert(isTinted)
                        {
                                updateShowDialog -> isTinted = updateShowDialog
                        }
                    }
                }
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .fillMaxHeight(0.5f)
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(10.dp),
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(painter = painterResource(id = R.drawable.logo),
                            contentDescription = "logo",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(120.dp)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .shadow(elevation = 0.4.dp),
                        )
                        Text(
                            text = jobContent.title,
                            fontFamily = BoldFont,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Normal
                            ),
                        )
                        Text(
                            text = jobContent.companyName,
                            fontFamily = BoldFont,
                            color = Color(0xFF2F4AE3),
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Normal
                            ),
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "",
                                tint = Color.LightGray,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                                text = jobContent.location,
                                fontFamily = NormalFont,
                                color = Color.Gray,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal
                                ),
                            )
                        }
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AttachMoney,
                                contentDescription = "",
                                tint = Color.LightGray,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                                text = "${((jobContent.minSalary * 10).roundToInt() / 10.0f) * 1000} - ${((jobContent.maxSalary * 10).roundToInt() / 10.0f) * 1000} USD/month",
                                fontFamily = NormalFont,
                                color = Color(0xFF2F4AE3),
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal
                                ),
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.horizontalScroll(rememberScrollState())
                        )
                        {
                            val list = jobContent.programmingLanguage
                            list.forEach{
                                jobTag(value = it)
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                        }
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
                            contentColor = Color(0xFF40A5FE),
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
                            0 -> TabContent("Job Description", jobContent.description)
                            1 -> TabContent("Benefits", jobContent.benefit)
                            2 -> TabContent("About",companyContent)
                        }
                    }

                }

            }
        }
    }
}

@Composable
fun TabContent(heading:String, content:String)
{

    Surface(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Text(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                text = heading,
                fontFamily = BoldFont,
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
                fontFamily = NormalFont,
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