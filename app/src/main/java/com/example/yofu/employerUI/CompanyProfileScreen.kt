package com.example.yofu.employerUI

import android.util.Log
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yofu.Company
import com.example.yofu.User
import com.example.yofu.accountManage.CompanyRepository
import com.example.yofu.accountManage.UserRepository
import com.example.yofu.accountUI.BoldFont
import com.example.yofu.accountUI.alert
import com.example.yofu.accountUI.extraBoldFont
import com.example.yofu.jobFinderUI.NormalFont
import com.example.yofu.jobFinderUI.convertDay
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun CompanyProfileScreen(navController: NavController, mainController: NavController)
{
    val userInfo = MutableStateFlow(User())
    val companyInfo = MutableStateFlow(Company())
    val userEmail = MutableStateFlow("")

    val auth = Firebase.auth
    UserRepository().fetch(auth.currentUser?.uid.toString()) { user, exception ->
        if (exception == null) {
            userEmail.value = auth.currentUser?.email.toString()
            userInfo.value = user ?: User()
            Log.d("ProfileView", "Get user successfully ${userInfo.value.uid}")
            Log.d("ProfileView", userInfo.value.toString())
            val userType = userInfo.value.userType
            if (userType == "Employer") {
                val companyRef = userInfo.value.cid
                CompanyRepository().fetch(companyRef) { company, e ->
                    if (e == null) {
                        companyInfo.value = company ?: Company()
                    }
                }
            }
        } else {
            Log.d("ProfileView", exception.toString())
        }
    }

    var showDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFFF6F7F9))
            .verticalScroll(rememberScrollState())
    ) {

        Column(modifier =Modifier.padding(10.dp)) {
            Text(
                text = "Profile",
                modifier = Modifier
                    .fillMaxWidth(),
                fontFamily = extraBoldFont,
                style = TextStyle(
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()){
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "",
                    tint = Color.LightGray,
                    modifier = Modifier.size(90.dp)
                )
                Column(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ){
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween)
                    {
                        Text(
                            text = companyInfo.collectAsState().value.name,
                            fontFamily = BoldFont,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,
                            )
                        )
                        IconButton(onClick = { showDialog = true}) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "",
                                tint = Color.Blue,
                                modifier = Modifier
                                    .size(15.dp)
                                    .align(Alignment.CenterVertically),

                                )
                        }
                    }

                    Text(
                        text = companyInfo.collectAsState().value.website,
                        fontFamily = NormalFont,
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
            Divider(modifier = Modifier.padding(start  = 5.dp, end = 5.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
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
                            .padding(top = 5.dp, start = 10.dp, end = 5.dp)
                            .align(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "",
                            tint = Color.Blue,
                            modifier = Modifier
                                .size(35.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Manager's Information",
                            fontFamily = BoldFont,
                            style = TextStyle(
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        IconButton(onClick = { showDialog = true}) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "",
                                tint = Color.LightGray,
                                modifier = Modifier
                                    .size(16.dp)
                                    .align(Alignment.CenterVertically),

                                )
                        }
                    }
                    Divider(modifier = Modifier.padding(start  = 5.dp, end = 5.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
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
                            text = userInfo.collectAsState().value.fullName,
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
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
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
                            text = userInfo.collectAsState().value.phone,
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
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "",
                            tint = Color.LightGray,
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = convertDay(userInfo.collectAsState().value.birthDate),
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
                            .padding(5.dp),
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
                            text = userEmail.collectAsState().value,
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

            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
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
                            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                            .align(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Default.Business,
                            contentDescription = "",
                            tint = Color.Blue,
                            modifier = Modifier
                                .size(35.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(
                            text = "Company's Information",
                            fontFamily = BoldFont,
                            style = TextStyle(
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        IconButton(onClick = { showDialog = true}) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "",
                                tint = Color.LightGray,
                                modifier = Modifier
                                    .size(16.dp)
                                    .align(Alignment.CenterVertically),

                                )
                        }
                    }
                    Divider(modifier = Modifier.padding(start  = 5.dp, end = 5.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Filled.BusinessCenter,
                            contentDescription = "",
                            tint = Color.LightGray,
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = companyInfo.collectAsState().value.name,
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
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
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
                            text = companyInfo.collectAsState().value.phone,
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
                            text = companyInfo.collectAsState().value.email,
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
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "",
                            tint = Color.LightGray,
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = companyInfo.collectAsState().value.location,
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
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            imageVector = Icons.Default.Link,
                            contentDescription = "",
                            tint = Color.LightGray,
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = companyInfo.collectAsState().value.website,
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

            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    mainController.navigate("Authentication")
                    {
                        popUpTo("Employer") {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(50.dp)
                    .padding(horizontal = 10.dp),
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(20.dp),
            ) {
                Row {
                    Text(
                        text = "Logout",
                        fontFamily = BoldFont,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal
                        ),
                        color = Color.Red
                    )
                    Icon(
                        imageVector = Icons.Filled.Logout,
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(20.dp)
                            .alignByBaseline()
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