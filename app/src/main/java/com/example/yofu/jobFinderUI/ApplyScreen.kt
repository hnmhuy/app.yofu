package com.example.yofu.jobFinderUI

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yofu.R
import com.example.yofu.accountUI.ButtonComponentWithLoading
import com.example.yofu.accountUI.TextFieldComponent
import com.example.yofu.accountUI.extraBoldFont
import com.example.yofu.accountUI.normalFont


@Composable
fun ApplyScreen(
    navController: NavController,
    applyViewModel: ApplyScreenViewModel = viewModel<ApplyScreenViewModel>(),
    vid: String
)
{
    var isApplying by remember { mutableStateOf(false) }

    var isUploading by remember { mutableStateOf(false) }

    var currentProgress by remember { mutableStateOf(0.0f) }

    applyViewModel.setVid(vid)

    var isUploaded by remember { mutableStateOf(false) }

    val pickPDFLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            Log.d("PDF", uri.toString())

            applyViewModel.setPdfURI(uri)

            isUploaded = !isUploaded
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFFF6F7F9)),
    )
    {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp))
        {
            Row(modifier = Modifier.fillMaxWidth()) {
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
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxHeight(0.9f)
            ) {
                TextFieldComponent(labelValue = "Adjust your contact email", setValue = {
                    applyViewModel.setNewEmail(it)
                })
                Spacer(modifier = Modifier.height(30.dp))
                TextFieldComponent(labelValue = "Adjust your phone email", setValue = {
                    applyViewModel.setNewPassword(it)
                })
                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        pickPDFLauncher.launch("application/pdf")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(150.dp),
                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    shape = RoundedCornerShape(20.dp),
                )
                {
                    if(!isUploading) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (!isUploaded) {
                                Icon(
                                    imageVector = Icons.Default.Upload,
                                    contentDescription = "",
                                    tint = Color(0xFF2F4AE3),
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
                            } else {
                                Icon(
                                    imageVector = Icons.Default.AttachFile,
                                    contentDescription = "",
                                    tint = Color.Red,
                                    modifier = Modifier.size(50.dp)
                                )
                                Text(
                                    text = "Uploaded Successfully",
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
                    else
                    {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .heightIn(min = 40.dp),
                            color = Color(0xFF2F4AE3),
                            strokeWidth = 5.dp,
                            progress = currentProgress
                        )
                        // Display the currentProcess value
                        Text(
                            text = "${currentProgress.toInt()}%",
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

            ButtonComponentWithLoading(value = "Apply", isLoading = isApplying)
            {
                isApplying = true
                applyViewModel.applyJob(
                    onProcess = {
                        isUploading = true
                        currentProgress = it
                        Log.d("Apply", it.toString())
                    }
                ) { success, message ->
                    if (success) {
                        isApplying = false
                        navController.popBackStack()
                    } else {
                        isApplying = false
                        Log.d("Apply", message)
                    }
                }
            }
        }
    }
}