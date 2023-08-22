package com.example.yofu.employerUI

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yofu.Company
import com.example.yofu.R
import com.example.yofu.accountManage.CompanyRepository
import com.example.yofu.accountManage.UserRepository
import com.example.yofu.accountUI.alert
import com.example.yofu.accountUI.extraBoldFont
import com.example.yofu.accountUI.normalFont
import com.example.yofu.jobFinderUI.NormalFont
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun CompanyScreen(
    navController: NavController
)
{
    val company = MutableStateFlow(Company())
//    var companyName = MutableStateFlow<String>("Sample company")
//    var aboutCompany = MutableStateFlow<String>("")
//    var companyLocation = MutableStateFlow<String>("")
//    var companyNumber = MutableStateFlow<String>("123456789")
//    var companyEmail = MutableStateFlow<String>("sample@gmail.com")
    val auth = Firebase.auth
    UserRepository().fetch(auth.currentUser!!.uid) { user, exception ->
        if (exception == null) {
            val companyRef = user!!.cid
            CompanyRepository().fetch(companyRef!!) { data, exception ->
                if (exception == null) {
                    company.value = data!!
                    Log.d("CompanyScreen", "Fetch company successfully")
                    Log.d("CompanyScreen", company.value.toString())
                }
            }
        }

    }
    var showDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .padding(start = 20.dp, end = 20.dp, top = 30.dp)
            .verticalScroll(rememberScrollState())
    )
    {
        Column()
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.33f),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Column()
                {
                    Text(
                        text = "Welcome",
                        fontFamily = extraBoldFont,
                        style = TextStyle(
                            fontSize = 27.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                        ),
                    )
                    Text(
                        text = "Have a nice day!",
                        fontFamily = normalFont,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal,
                        ),
                    )
                }
                Image(painter = painterResource(id = R.drawable.bro),
                    contentDescription = "bro",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(150.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(10.dp))
                )
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)){
                Image(painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(80.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(10.dp))
                )
                Column(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ){
                    Row(modifier = Modifier.fillMaxWidth())
                    {
                        Text(
                            text = company.collectAsState().value.name,
                            fontFamily = extraBoldFont,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,
                            ),
                            color = Color.Blue
                        )
                        IconButton(onClick = { showDialog = true}) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "",
                                tint = Color.Blue,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.CenterVertically),

                                )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))


            }

            section("About company", company.collectAsState().value.description)
            section(heading = "Location", content = company.collectAsState().value.location)
            Text(
                text = "Contact us",
                fontFamily = extraBoldFont,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                ),
                color = Color.Black,
                modifier = Modifier.padding(top = 1.dp, start = 20.dp, end = 20.dp)
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .fillMaxHeight(0.4f),
                shape = RoundedCornerShape(20.dp),
                elevation = 3.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Filled.Phone,
                            contentDescription = "",
                            tint = Color.LightGray,
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = company.collectAsState().value.phone,
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
                    ){
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
                            text = company.collectAsState().value.email,
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

@Composable
fun section(heading:String, content:String)
{
    Text(
        text = heading,
        fontFamily = extraBoldFont,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
        ),
        color = Color.Black,
        modifier = Modifier.padding(top = 1.dp, start = 20.dp, end = 20.dp)
    )

    Text(
        text = content,
        fontFamily = NormalFont,
        style = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
        ),
        color = Color.Black,
        modifier = Modifier.padding(start = 15.dp, end = 20.dp, bottom = 20.dp),
    )

}