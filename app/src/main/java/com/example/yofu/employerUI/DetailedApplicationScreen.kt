package com.example.yofu.employerUI

import android.content.ActivityNotFoundException
import android.content.Intent
import android.util.Log
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
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.yofu.JobApplication
import com.example.yofu.R
import com.example.yofu.User
import com.example.yofu.accountManage.UserRepository
import com.example.yofu.accountUI.BoldFont
import com.example.yofu.accountUI.LoadingScreen
import com.example.yofu.accountUI.alert
import com.example.yofu.accountUI.extraBoldFont
import com.example.yofu.accountUI.normalFont
import com.example.yofu.jobFinderUI.NormalFont
import com.example.yofu.jobVacancyManage.ApplyRepository
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import io.grpc.android.BuildConfig
import kotlinx.coroutines.delay
import java.io.File
import java.util.Objects


@Composable
fun DetailedApplicationScreen(
    navController: NavController,
    aid: String,
    applicationsListViewModel: ApplicationsListViewModel = ApplicationsListViewModel()
)
{
    var loading by remember { mutableStateOf(true) }
    var userInfo by remember { mutableStateOf(User()) }
    var applicationInfo by remember { mutableStateOf(JobApplication()) }
    var fileSize by remember { mutableStateOf("") }
    var fileName by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var reject by remember { mutableStateOf(false) }
    var accept by remember { mutableStateOf(false) }

    var message by remember { mutableStateOf("") }

    var isDownloading by remember { mutableStateOf(false) }

    var isDownloaded by remember { mutableStateOf(false) }

    var processPercentage by remember { mutableStateOf(0) }

    var fileData by remember { mutableStateOf(File(""))}

    val context = LocalContext.current

    val intent = Intent(Intent.ACTION_VIEW)


    LaunchedEffect(loading)
    {

        ApplyRepository().fetchAnApplication(aid) { application, exception ->
            if (exception == null) {
                applicationInfo = application!!

                applicationsListViewModel.loadVacancyInfo(applicationInfo.vid)

                if (applicationInfo.status != null && applicationInfo.status == true)
                {
                    accept = true
                }
                else if (applicationInfo.status != null && applicationInfo.status == false)
                {
                    reject = true
                }
                else
                {
                    accept = false
                    reject = false
                }
                UserRepository().fetch(application.uid) { user, exception ->
                    if (exception == null) {
                        userInfo = user!!
                        if (applicationInfo.newEmail != "")
                        {
                            userInfo.email = applicationInfo.newEmail
                        }

                        if (applicationInfo.newPhone != "")
                        {
                            userInfo.phone = applicationInfo.newPhone
                        }

                        // Get the file size
                        val storageRef = Firebase.storage.reference
                        val pathReference: StorageReference = storageRef.child("pdf/${application.cvFileName}")
                        pathReference.metadata.addOnSuccessListener { storageMetadata ->
                            fileSize = "${storageMetadata.sizeBytes / 1024} KB"
                        }.addOnFailureListener {
                            Log.w("ApplicationList", it)
                        }

                        fileName = "CV"

                        loading = false
                    } else {
                        Log.w("ApplicationList", exception)
                    }
                }
            } else {
                Log.w("ApplicationList", exception)
            }
        }
        delay(1000)
        loading = false
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFFF6F7F9))
            .padding(15.dp)
    ) {
        if(loading)
        {
            LoadingScreen(isLoading = loading)
        }
        else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            )
            {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
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
                                text = userInfo.fullName,
                                fontFamily = extraBoldFont,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 35.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Normal,
                                )
                            )

                            Text(
                                text = userInfo.email,
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
                                    text = userInfo.fullName,
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
                                    text = userInfo.phone,
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
                                    text = userInfo.email,
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
                                Image(
                                    painter = painterResource(id = R.drawable.pdf),
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
                                            fontSize = 12.sp,
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
                                if (!isDownloading) {
                                    Button(
                                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                                        elevation = ButtonDefaults.elevation(0.dp),
                                        onClick = {
                                            if (!isDownloaded) {
                                                isDownloading = true
                                                ApplyRepository().downloadCV(
                                                    applicationData = applicationInfo,
                                                    userInfo = userInfo,
                                                    context = context,
                                                    onComplete = { file, isSuccess ->
                                                        isDownloading = false
                                                        if (isSuccess) {
                                                            fileData = file
                                                            isDownloaded = true
                                                        }
                                                    },
                                                    onProgress = {
                                                        isDownloading = true
                                                        processPercentage = it
                                                    }
                                                )
                                            } else {
                                                val uri = FileProvider.getUriForFile(
                                                    context,
                                                    "com.example.yofu.fileprovider",
                                                    fileData
                                                )
                                                intent.setDataAndType(uri, "application/pdf")
                                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                                try {
                                                    context.startActivity(intent)
                                                } catch (e: ActivityNotFoundException) {
                                                    // Handle case where no app can handle the intent
                                                }
                                            }
                                        }
                                    ) {
                                        if (!isDownloaded) Icon(
                                            imageVector = Icons.Filled.FileDownload,
                                            contentDescription = "",
                                            tint = Color(0xFFFF6E58),
                                            modifier = Modifier
                                                .size(30.dp)
                                        )
                                        else {
                                            Icon(
                                                imageVector = Icons.Filled.FileOpen,
                                                contentDescription = "",
                                                tint = Color(0xFF2F4AE3),
                                                modifier = Modifier
                                                    .size(30.dp)
                                            )
                                        }
                                    }
                                }
                                else
                                {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(30.dp),
                                        progress = processPercentage.toFloat()
                                    )
                                }
                            }

                            Divider(modifier = Modifier.padding(start = 5.dp, end = 10.dp))
                        }
                    }

                }



                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {

                    Button(
                        onClick = {
                            ApplyRepository().rejectApplication(aid) { it ->
                                if (it == "Successfully reject application") {
                                    showDialog = true
                                    message = "Successfully reject application"
                                    reject = true
                                } else {
                                    showDialog = true
                                    message = "Failed to reject application"
                                    reject = false
                                }
                            }
                            reject = true },
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
                            text = if (reject == true) "Rejected" else "Reject",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = boldFont,
                            color = if (accept == false) Color(0xFFF85F5F) else Color.White
                        )
                    }

                    Button(
                        onClick = {
                            ApplyRepository().acceptApplication(aid) { it ->
                                if (it == "Successfully accept application") {
                                    showDialog = true
                                    message = "Successfully accept application"
                                    accept = true
                                } else {
                                    showDialog = true
                                    message = "Failed to accept application"
                                    accept = false
                                }
                            }
                            accept = true },
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
                            text = if (accept == true) "Accepted" else "Accept",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = boldFont,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
    if(showDialog)
    {
        alert(showDialog, title = "Notification", message)
        {
                updateShowDialog -> showDialog = updateShowDialog
        }
    }
}