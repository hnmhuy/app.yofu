package com.example.yofu.employer


import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yofu.R
import com.example.yofu.accountUI.TextFieldComponent
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.toSize
import androidx.compose.material.Text
import androidx.compose.material.RangeSlider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.internal.enableLiveLiterals
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.Calendar
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.yofu.Screen
import com.example.yofu.accountUI.BoldFont
import com.example.yofu.accountUI.ButtonComponentWithLoading
import com.example.yofu.accountUI.NormalTextComponentWithSize
import com.example.yofu.accountUI.NotCenterBoldTextComponentWithSize
import com.example.yofu.accountUI.dockedDatePicker
import com.example.yofu.accountUI.extraBoldFont
import com.example.yofu.accountUI.normalFont
import com.example.yofu.employerUI.CreateVacancyViewModel
import com.example.yofu.jobFinderUI.convertDay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import kotlin.math.ceil
import kotlin.math.roundToInt


private fun roundToNearestTenth(value: Float): Float {
    return (value * 10).roundToInt() / 10.0f
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MyUI(setSalary: (Float, Float) -> Unit) {

    var sliderValues by remember {
        mutableStateOf(0f..20f) // pass the initial values
    }

    RangeSlider(

        value = sliderValues,
        onValueChange = { sliderValues_ ->
            sliderValues = sliderValues_
        },
        valueRange = 0f..20f,
        colors = SliderDefaults.colors(
            activeTrackColor = Color(0xFF40A5FE),
            thumbColor = Color(0xFF40A5FE)
        ),
        onValueChangeFinished = {
            // this is called when the user completed selecting the value
            Log.d(
                "MainActivity",
                "Start: ${sliderValues.start}, End: ${sliderValues.endInclusive}"
            )
            setSalary(sliderValues.start, sliderValues.endInclusive)
        }
    )

    NormalTextComponentWithSize("${
        roundToNearestTenth(sliderValues.start)
    }k to ${
        roundToNearestTenth(sliderValues.endInclusive)
    }k USD/MONTH", 15.sp)
}


@Composable
fun DropDown(label: String, list: List<String>, previousContent: String = "", setValue: (String) -> Unit) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var selectedItem by remember{
        mutableStateOf(previousContent)
    }
    var textFieldSize by remember{
        mutableStateOf(Size.Zero)
    }
    val icon = if(isExpanded){
        Icons.Filled.KeyboardArrowUp
    }else{
        Icons.Filled.KeyboardArrowDown
    }

    Column{
        OutlinedTextField(
            enabled = false,
            value = selectedItem,
            textStyle = TextStyle(color = Color.Black),
            onValueChange = { selectedItem = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    textFieldSize = layoutCoordinates.size
                        .toSize()
                },
            shape = RoundedCornerShape(50.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                cursorColor = Color.Black,
                disabledTextColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray,
                trailingIconColor = Color.LightGray
            ),
            label = {
                Text(text = label,
                fontFamily = normalFont,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                )) },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    modifier = Modifier.clickable { isExpanded = !isExpanded }
                )
            }

        )
        DropdownMenu(expanded = isExpanded,
            onDismissRequest = { isExpanded  = false },
            modifier = Modifier.fillMaxWidth()
        )
        {
            list.forEach { label-> DropdownMenuItem(
                onClick = { selectedItem = label
                        isExpanded = false
                        setValue(label)}
            )
            {
                Text(text = label,
                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                    )
                )
            }
            }
        }
    }
}


@Composable
fun JobTypeCheckbox(setJobType: (String)-> Unit) {
    val selectedCheckbox = remember { mutableStateOf(0) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 1
                            setJobType("Full Time")
                            Log.d("JobType", "Full Time")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 1) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 1) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 1) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Full Time",
                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 2
                            setJobType("Contract")
                            Log.d("JobType", "Contract")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 2) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 2) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 2) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Contract",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 3
                            setJobType("Internship")
                            Log.d("JobType", "Internship")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 3) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 3) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 3) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Internship",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 4
                            setJobType("Part Time")
                            Log.d("JobType", "Part Time")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 4) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 4) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 4) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Part Time",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 5
                            setJobType("Temporary")
                            Log.d("JobType", "Temporary")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 5) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 5) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 5) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Temporary",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 6
                            setJobType("Other")
                            Log.d("JobType", "Other")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 6) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 6) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 6) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Other",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
        }
    }
}

@Composable
fun PositionCheckbox(
    setJobPosition: (String) -> Unit
) {
    val selectedCheckbox = remember { mutableStateOf(0) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 1
                            setJobPosition("Software Engineer")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 1) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 1) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 1) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Software Engineer",
                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 2
                            setJobPosition("Fullstack Engineer")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 2) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 2) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 2) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Fullstack Engineer",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 3
                            setJobPosition("Security Engineer")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 3) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 3) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 3) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Security Engineer",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 4
                            setJobPosition("Business Analysist")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 4) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 4) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 4) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Business Analysist",
                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 5
                            setJobPosition("Web Designer")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 5) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 5) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 5) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Web Designer",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 6
                            setJobPosition("Game Developer")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 6) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 6) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 6) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Game Developer",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 7
                            setJobPosition("Developer")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 7) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 7) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 7) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Developer",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 8
                            setJobPosition("Data Scientist")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 8) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 8) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 8) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Data Scientist",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 9
                            setJobPosition("Tester")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 9) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 9) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 9) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Tester",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 10
                            setJobPosition("Front-end")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 10) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 10) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 10) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Front-end",
                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 11
                            setJobPosition("Back-end")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 11) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 11) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 11) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Back-end",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            selectedCheckbox.value = 12
                            setJobPosition("Other")
                        }
                        .background(
                            color = if (selectedCheckbox.value == 12) Color(0xFF40A5FE) else Color.White,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox.value == 12) Color(0xFF40A5FE) else Color.Gray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox.value == 12) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Other",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
        }
    }
}
@Composable
fun ProgrammingLanguageCheckbox(updateProgammingLanguage: (Int) -> Unit) {
    var selectedCheckbox1 by remember {
        mutableStateOf(false)
    }
    var selectedCheckbox2 by remember {
        mutableStateOf(false)
    }
    var selectedCheckbox3 by remember {
        mutableStateOf(false)
    }
    var selectedCheckbox4 by remember {
        mutableStateOf(false)
    }
    var selectedCheckbox5 by remember {
        mutableStateOf(false)
    }
    var selectedCheckbox6 by remember {
        mutableStateOf(false)
    }
    var selectedCheckbox7 by remember {
        mutableStateOf(false)
    }
    var selectedCheckbox8 by remember {
        mutableStateOf(false)
    }
    var selectedCheckbox9 by remember {
        mutableStateOf(false)
    }
    var selectedCheckbox10 by remember {
        mutableStateOf(false)
    }
    var selectedCheckbox11 by remember {
        mutableStateOf(false)
    }
    var selectedCheckbox12 by remember {
        mutableStateOf(false)
    }


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = RoundedCornerShape(size = 6.dp))
                        .clickable {
                            selectedCheckbox1 = !selectedCheckbox1
                            updateProgammingLanguage(0)
                        }
                        .background(
                            color = if (selectedCheckbox1) Color(0xFF40A5FE) else Color.White,
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox1) Color(0xFF40A5FE) else Color.Gray,
                            shape = RoundedCornerShape(size = 6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox1) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Java Script",
                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = RoundedCornerShape(size = 6.dp))
                        .clickable {
                            selectedCheckbox2 = !selectedCheckbox2
                            updateProgammingLanguage(1)
                        }
                        .background(
                            color = if (selectedCheckbox2) Color(0xFF40A5FE) else Color.White,
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox2) Color(0xFF40A5FE) else Color.Gray,
                            shape = RoundedCornerShape(size = 6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox2) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Java",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = RoundedCornerShape(size = 6.dp))
                        .clickable {
                            selectedCheckbox3 = !selectedCheckbox3
                            updateProgammingLanguage(2)
                        }
                        .background(
                            color = if (selectedCheckbox3) Color(0xFF40A5FE) else Color.White,
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox3) Color(0xFF40A5FE) else Color.Gray,
                            shape = RoundedCornerShape(size = 6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox3) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Kotlin",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = RoundedCornerShape(size = 6.dp))
                        .clickable {
                            selectedCheckbox4 = !selectedCheckbox4
                            updateProgammingLanguage(3)
                        }
                        .background(
                            color = if (selectedCheckbox4) Color(0xFF40A5FE) else Color.White,
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox4) Color(0xFF40A5FE) else Color.Gray,
                            shape = RoundedCornerShape(size = 6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox4) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("PHP",
                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = RoundedCornerShape(size = 6.dp))
                        .clickable {
                            selectedCheckbox5 = !selectedCheckbox5
                            updateProgammingLanguage(4)
                        }
                        .background(
                            color = if (selectedCheckbox5) Color(0xFF40A5FE) else Color.White,
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox5) Color(0xFF40A5FE) else Color.Gray,
                            shape = RoundedCornerShape(size = 6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox5) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("C#",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = RoundedCornerShape(size = 6.dp))
                        .clickable {
                            selectedCheckbox6 = !selectedCheckbox6
                            updateProgammingLanguage(5)
                        }
                        .background(
                            color = if (selectedCheckbox6) Color(0xFF40A5FE) else Color.White,
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox6) Color(0xFF40A5FE) else Color.Gray,
                            shape = RoundedCornerShape(size = 6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox6) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("C/C++",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = RoundedCornerShape(size = 6.dp))
                        .clickable {
                            selectedCheckbox7 = !selectedCheckbox7
                            updateProgammingLanguage(6)
                        }
                        .background(
                            color = if (selectedCheckbox7) Color(0xFF40A5FE) else Color.White,
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox7) Color(0xFF40A5FE) else Color.Gray,
                            shape = RoundedCornerShape(size = 6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox7) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("HTML",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = RoundedCornerShape(size = 6.dp))
                        .clickable {
                            selectedCheckbox8 = !selectedCheckbox8
                            updateProgammingLanguage(7)
                        }
                        .background(
                            color = if (selectedCheckbox8) Color(0xFF40A5FE) else Color.White,
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox8) Color(0xFF40A5FE) else Color.Gray,
                            shape = RoundedCornerShape(size = 6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox8) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("CSS",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = RoundedCornerShape(size = 6.dp))
                        .clickable {
                            selectedCheckbox9 = !selectedCheckbox9
                            updateProgammingLanguage(8)
                        }
                        .background(
                            color = if (selectedCheckbox9) Color(0xFF40A5FE) else Color.White,
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox9) Color(0xFF40A5FE) else Color.Gray,
                            shape = RoundedCornerShape(size = 6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox9) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Matlab",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = RoundedCornerShape(size = 6.dp))
                        .clickable {
                            selectedCheckbox10 = !selectedCheckbox10
                            updateProgammingLanguage(9)
                        }
                        .background(
                            color = if (selectedCheckbox10) Color(0xFF40A5FE) else Color.White,
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox10) Color(0xFF40A5FE) else Color.Gray,
                            shape = RoundedCornerShape(size = 6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox10) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("TypeScript",
                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = RoundedCornerShape(size = 6.dp))
                        .clickable {
                            selectedCheckbox11 = !selectedCheckbox11
                            updateProgammingLanguage(10)
                        }
                        .background(
                            color = if (selectedCheckbox11) Color(0xFF40A5FE) else Color.White,
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox11) Color(0xFF40A5FE) else Color.Gray,
                            shape = RoundedCornerShape(size = 6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox11) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("SQL",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size = 20.dp)
                        .clip(shape = RoundedCornerShape(size = 6.dp))
                        .clickable {
                            selectedCheckbox12 = !selectedCheckbox12
                            updateProgammingLanguage(11)
                        }
                        .background(
                            color = if (selectedCheckbox12) Color(0xFF40A5FE) else Color.White,
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (selectedCheckbox12) Color(0xFF40A5FE) else Color.Gray,
                            shape = RoundedCornerShape(size = 6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedCheckbox12) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text("Other",

                    fontFamily = normalFont,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),)
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateVacancy(
    navController: NavController,
    viewModel: CreateVacancyViewModel = CreateVacancyViewModel()
) = Surface (
    modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF6F7F9))
)
{
    var isLoading by remember{
        mutableStateOf(false)
    }
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
    Column(modifier = Modifier
        .background(Color(0xFFF6F7F9))
        .verticalScroll(rememberScrollState()))
    {
        Image(painter = painterResource(id = R.drawable.createjob),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )

        Column(modifier = Modifier.padding(28.dp)){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10))
                    .background(Color.White)
                    .padding(18.dp))
            {
                Column{
                    NotCenterBoldTextComponentWithSize(value = "Job Title", 20.sp)
                    Divider(startIndent = 1.dp, thickness = 0.1.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(5.dp))
                    TextFieldComponent(labelValue = "Job's Title",
                        setValue = {
                            viewModel.setTitle(it)
                        })
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10))
                    .background(Color.White)
                    .padding(18.dp))
            {
                Column{
                    NotCenterBoldTextComponentWithSize(value = "Location", size = 20.sp)
                    Divider(startIndent = 1.dp, thickness = 0.2.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(5.dp))
                    DropDown("Select location", list,
                        setValue = {
                            viewModel.setLocation(it)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10))
                    .background(Color.White)
                    .padding(18.dp))
            {
                Column{
                    NotCenterBoldTextComponentWithSize(value = "Salary", size = 20.sp)
                    Divider(startIndent = 1.dp, thickness = 0.2.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(5.dp))
                    MyUI(
                        setSalary = {min, max ->
                            viewModel.setSalary(min, max)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10))
                    .background(Color.White)
                    .padding(18.dp))
            {
                Column{
                    NotCenterBoldTextComponentWithSize(value = "Due Date", size = 20.sp)
                    Divider(startIndent = 1.dp, thickness = 0.2.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(5.dp))
                    dockedDatePicker(isOpenDialog = isOpenDialog) {
                        viewModel.setDueDate(it)
                    }
            }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10))
                    .background(Color.White)
                    .padding(18.dp))
            {
                Column{
                    NotCenterBoldTextComponentWithSize(value = "Job Type", size = 20.sp)
                    Divider(startIndent = 1.dp, thickness = 0.2.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(10.dp))
                    JobTypeCheckbox(
                        setJobType = {
                            viewModel.setJobType(it)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10))
                    .background(Color.White)
                    .padding(18.dp))
            {
                Column{
                    NotCenterBoldTextComponentWithSize(value = "Position", size = 20.sp)
                    Divider(startIndent = 1.dp, thickness = 0.2.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(10.dp))
                    PositionCheckbox(
                        setJobPosition = {
                            viewModel.setPosition(it)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10))
                    .background(Color.White)
                    .padding(18.dp))
            {
                Column{
                    NotCenterBoldTextComponentWithSize(value = "Programming Language", size = 20.sp)
                    Divider(startIndent = 1.dp, thickness = 0.2.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(10.dp))
                    ProgrammingLanguageCheckbox(
                        updateProgammingLanguage = {
                            viewModel.updateProgram(it)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10))
                    .background(Color.White)
                    .padding(18.dp))
            {
                Column{
                    NotCenterBoldTextComponentWithSize(value = "Job Description", size = 20.sp)
                    Divider(startIndent = 1.dp, thickness = 0.2.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(10.dp))
                    val textValue = remember {
                        mutableStateOf("")
                    }
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(BorderStroke(0.1.dp, Color.Transparent)),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            cursorColor = Color.Black,
                            disabledTextColor = Color.Transparent,
                            unfocusedBorderColor = Color.LightGray
                        ),
                        keyboardOptions = KeyboardOptions.Default,
                        shape = RoundedCornerShape(50.dp),
                        value = textValue.value,
                        onValueChange = {
                            textValue.value = it
                            viewModel.setDescription(it)
                        },)
                    Spacer(modifier = Modifier.height(20.dp))

                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10))
                    .background(Color.White)
                    .padding(18.dp))
            {
                Column{
                    NotCenterBoldTextComponentWithSize(value = "Job Benefit", size = 20.sp)
                    Divider(startIndent = 1.dp, thickness = 0.2.dp, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(10.dp))
                    val textValue = remember {
                        mutableStateOf("")
                    }
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(BorderStroke(0.1.dp, Color.Transparent)),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            cursorColor = Color.Black,
                            disabledTextColor = Color.Transparent,
                            unfocusedBorderColor = Color.LightGray
                        ),
                        keyboardOptions = KeyboardOptions.Default,
                        shape = RoundedCornerShape(50.dp),
                        value = textValue.value,
                        onValueChange = {
                            textValue.value = it
                            viewModel.setBenefit(it)
                        },)
                    Spacer(modifier = Modifier.height(20.dp))

                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp)
        ){
//            Button(
//                onClick = {
//                    var message = ""
//                    if(viewModel.verify { message = it })
//                    {
//                        viewModel.createVacancy {
//                            if (it == null)
//                            {
//                                Toast.makeText(toastContex, "Create vacancy Sucessfully", Toast.LENGTH_SHORT).show()
//                                navController.navigate(Screen.CreatedVacanciesList.name)
//                            }
//                            else
//                            {
//                                Toast.makeText(toastContex, "Create vacancy Failed", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    }
//                    else
//                        Toast.makeText(toastContex, message, Toast.LENGTH_SHORT).show()
//                },
//                modifier = Modifier
//                    .clip(RoundedCornerShape(50))
//                    .fillMaxWidth()
//                    .height(60.dp),
//                colors = ButtonDefaults.buttonColors(
//                    backgroundColor = Color(0xFF2F4AE3)
//                ),
//            ){
//                Text(text = "Create",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .heightIn(min = 50.dp),
//                    fontFamily = BoldFont,
//                    color = Color.White,
//                    style = TextStyle(
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Normal,
//                        fontStyle = FontStyle.Normal
//                    ),
//                    textAlign = TextAlign.Center)
//            }
            var message = ""
            ButtonComponentWithLoading(value = "Create", isLoading = isLoading) {
                if(viewModel.verify { message = it })
                {
                    isLoading = true
                    viewModel.createVacancy {
                        if (it == null)
                        {
                            isLoading = false
                            Toast.makeText(toastContex, "Create vacancy Sucessfully", Toast.LENGTH_SHORT).show()
                            navController.navigate(Screen.CreatedVacanciesList.name) {
                                popUpTo(Screen.CreateVacancy.name) {
                                    inclusive = true
                                }
                            }
                        }
                        else
                        {
                            isLoading = false
                            Toast.makeText(toastContex, "Create vacancy Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else
                {
                    Toast.makeText(toastContex, message, Toast.LENGTH_SHORT).show()
                    isLoading = false
                }

            }
        }
    }
}
