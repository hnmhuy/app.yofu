package com.example.yofu.jobFinderUI

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yofu.R
import com.example.yofu.accountUI.NotCenterBoldTextComponentWithSize
import com.example.yofu.accountUI.TextFieldComponent
import com.example.yofu.accountUI.extraBoldFont
import com.example.yofu.employer.DatePickerScreen
import com.example.yofu.employer.DropDown
import com.example.yofu.employer.JobTypeCheckbox
import com.example.yofu.employer.PositionCheckbox
import com.example.yofu.employer.ProgrammingLanguageCheckbox
@Preview
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search() {
    var textValue by remember {
        mutableStateOf("")
    }
    var active by remember{
        mutableStateOf(false
        )
    }
    var items = remember{
        mutableStateListOf(
            "banana",
            "ananas"
        )
    }
    Scaffold{
        Column (
//            modifier = Modifier.verticalScroll(rememberScrollState())
            )
        {
            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = com.example.yofu.R.drawable.findjob),
                contentDescription = "",
                contentScale = androidx.compose.ui.layout.ContentScale.FillWidth,
                modifier = androidx.compose.ui.Modifier
                    .fillMaxWidth()
            )
            androidx.compose.foundation.layout.Column(
                modifier = androidx.compose.ui.Modifier.padding(28.dp)
            ) {
                androidx.compose.material3.SearchBar(query = textValue,
                    onQueryChange = {

                        textValue = it
                    },
                    onSearch = {
                        if (textValue.isNotEmpty()) {
                            items.add(0, textValue)
                        }
                        active = false
                    },
                    active = active,
                    onActiveChange = {
                        active = it
                    },
                    placeholder = {
                        androidx.compose.material.Text(text = "Search")
                    },
                    leadingIcon = {
                        androidx.compose.material.Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.Search,
                            contentDescription = "Search icon"
                        )
                    },
                    trailingIcon = {
                        if (active) {
                            androidx.compose.material.Icon(
                                modifier = androidx.compose.ui.Modifier.clickable {
                                    if (textValue.isNotEmpty()) {
                                        textValue = ""
                                    } else {
                                        active = false
                                    }
                                },
                                imageVector = androidx.compose.material.icons.Icons.Default.Close,
                                contentDescription = "Search icon"
                            )
                        }
                    }
                ) {
                    items.forEach {
                        if (items.isNotEmpty()) {
                            androidx.compose.foundation.layout.Row(
                                modifier = androidx.compose.ui.Modifier.padding(
                                    28.dp
                                )
                            ) {
                                androidx.compose.material.Icon(
                                    imageVector = androidx.compose.material.icons.Icons.Default.History,
                                    contentDescription = "Search history"
                                )
                                androidx.compose.foundation.layout.Spacer(
                                    modifier = androidx.compose.ui.Modifier.width(
                                        10.dp
                                    )
                                )
                                androidx.compose.material.Text(text = it)
                            }
                        }
                    }
                }
            }
        }
    }

}
