package com.example.yofu.accountUI

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.material.Icon
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.yofu.R
import com.example.yofu.Screen
import com.example.yofu.Vacancy
import kotlinx.coroutines.launch
import java.util.Date
import kotlin.math.roundToInt


val normalFont = FontFamily(
    Font(R.font.raleway_regular, FontWeight.Thin),
)
val extraBoldFont = FontFamily(
    Font(R.font.raleway_black, FontWeight.Black),
)
val BoldFont = FontFamily(
    Font(R.font.raleway_bold, FontWeight.Bold),
)
@Composable
fun NormalTextComponent(value: String)
{
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        fontFamily = normalFont,
        style = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun ExtraBoldTextComponent(value: String)
{
    Text(
        text = value,
        modifier = Modifier
            .heightIn(min = 55.dp)
            .offset(
                x = 0.dp,
                y = 0.dp
            )
            .alpha(1.75f),
        fontFamily = extraBoldFont,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 42.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
        )

    )
}

@Composable
fun BoldTextComponent(value: String)
{   val extraBoldFont = FontFamily(
    Font(R.font.raleway_bold, FontWeight.Bold),
)
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        fontFamily = extraBoldFont,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 27.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
        ),
    )
}

@Composable
fun LessBoldTextComponent(value: String)
{   val extraBoldFont = FontFamily(
    Font(R.font.raleway_bold, FontWeight.Bold),
)
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 20.dp),
        fontFamily = extraBoldFont,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
        ),
    )
}
@Composable
fun TextFieldComponent(
    labelValue: String,
    previousContent: String = "",
    setValue: (String) -> Unit
)
{
    val textValue = remember {
        mutableStateOf(previousContent)
    }
    var textFieldSize by remember{
        mutableStateOf(Size.Zero)
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { layoutCoordinates ->
                textFieldSize = layoutCoordinates.size
                    .toSize()
            },
        textStyle = TextStyle(color = Color.Black),
        label = {
            Text(
                text = labelValue,
                fontFamily = normalFont,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                ),
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            cursorColor = Color.Black,
            disabledTextColor = Color.Transparent,
            unfocusedBorderColor = Color.LightGray
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default,
        shape = RoundedCornerShape(30.dp),
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            setValue(it)
        },
    )
}

@Composable
fun DescriptionTextFieldComponent(
    labelValue: String,
    setValue: (String) -> Unit
)
{
    val textValue = remember {
        mutableStateOf("")
    }
    var textFieldSize by remember{
        mutableStateOf(Size.Zero)
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { layoutCoordinates ->
                textFieldSize = layoutCoordinates.size
                    .toSize()
            },
        label = {Text(
            text = labelValue,
            fontFamily = normalFont,
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
            )
        )},
        textStyle = TextStyle(color = Color.Black),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            cursorColor = Color.Black,
            disabledTextColor = Color.Transparent,
            unfocusedBorderColor = Color.LightGray
        ),
        keyboardOptions = KeyboardOptions.Default,
        shape = RoundedCornerShape(30.dp),
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            setValue(it)
        },
    )
}
@Composable
fun PasswordTextFieldComponent(
    labelValue: String,
    previousContent: String = "",
    setValue: (String) -> Unit = {}
    )
{
    val password = remember {
        mutableStateOf(previousContent)
    }
    val passwordVisible = remember {
        mutableStateOf(false)
    }
    var textFieldSize by remember{
        mutableStateOf(Size.Zero)
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { layoutCoordinates ->
                textFieldSize = layoutCoordinates.size
                    .toSize()
            },
        shape = RoundedCornerShape(30.dp),
        textStyle = TextStyle(color = Color.Black),
        label = {Text(
            text = labelValue,
            fontFamily = normalFont,
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
            )
        )},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            cursorColor = Color.Black,
            disabledTextColor = Color.Transparent,
            unfocusedBorderColor = Color.LightGray,
            trailingIconColor = Color.LightGray
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        value = password.value,
        onValueChange = {
            password.value = it
            setValue(it)
        },
        trailingIcon = {
            val iconImage = if(passwordVisible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            val description = if(passwordVisible.value)
            {
                "Hide password"
            }else
            {
                "Show password"
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun ButtonComponent(
    value: String,
    callback: () -> Unit = {}
    )
{
    Button(
        onClick = { callback() },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(50.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color(0XFF2F4AE3)),
        shape = RoundedCornerShape(50.dp)
    ) {
        val boldFont = FontFamily(
            Font(R.font.raleway_bold, FontWeight.Bold),
        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = boldFont,
            color = Color.White,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable
fun ButtonComponentWithLoading(
    value: String,
    isLoading: Boolean,
    callback: () -> Unit = {}
)
{
    Button(
        enabled = !isLoading,
        onClick = { callback() },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(50.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color(0XFF2F4AE3)),
        shape = RoundedCornerShape(50.dp)
    ) {
        val boldFont = FontFamily(
            Font(R.font.raleway_bold, FontWeight.Bold),
        )
        if (isLoading)
        {
            CircularProgressIndicator(
                color = Color.White,
            )
        }
        else
        {
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = boldFont,
                color = Color.White,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun DividerTextComponent()
{
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.LightGray,
            thickness = 1.dp
        )
        Text(
            text = "@YOFU App Demo",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = normalFont,
            color = Color.LightGray,
            modifier = Modifier.padding(8.dp),
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.LightGray,
            thickness = 1.dp
        )
    }
}

@Composable
fun ClickableLoginTextComponent(onTextSelected:(String) ->Unit)
{
    val initialText = "Do not have an account? "
    val signUp = "Sign up"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Color(0xFF2F4AE3))){
            pushStringAnnotation(tag = signUp, annotation = signUp)
            append(signUp)
        }
    }
    ClickableText(
        modifier = Modifier
            .fillMaxSize()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center,
            fontFamily = normalFont
        ),
        text = annotatedString, onClick = {offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also{span->
                    Log.d("ClickableTextComponent", "{${span.item}}")

                    if (span.item == signUp)
                    {
                        onTextSelected(span.item)
                    }
                }


        })
}


@Composable
fun NormalTextComponentWithSize(value: String, size: TextUnit)
{
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        fontFamily = normalFont,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = size,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
    )
}
@Composable
fun NotCenterNormalTextComponent(value: String, size: TextUnit)
{
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        fontFamily = normalFont,
        style = TextStyle(
            fontSize = size,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
    )
}
@Composable
fun NotCenterBoldTextComponentWithSize(value: String, size: TextUnit)
{   val extraBoldFont = FontFamily(
    Font(R.font.raleway_bold, FontWeight.Bold),
)
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        fontFamily = extraBoldFont,
        style = TextStyle(
            fontSize = size,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
        ),
    )
}
@Composable
fun NotCenterBoldTextComponent(value: String)
{   val extraBoldFont = FontFamily(
    Font(R.font.raleway_bold, FontWeight.Bold),
)
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        fontFamily = extraBoldFont,
        style = TextStyle(
            fontSize = 27.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
        ),
    )
}

@Composable
fun alert(showDialog: Boolean, title: String = "Oops, Failed!", message: String = "Sorry this feature is not supported yet! Please try again later", onClose: (Boolean)-> Unit)
{
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .padding(20.dp),
        onDismissRequest = { onClose(!showDialog) },
        title = {
            Text(text = title,
                fontFamily = BoldFont,
                color = if(title == "Oops, Failed!") Color.Red else Color(0xFF2F4AE3),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal),
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(text = message,
                fontFamily = normalFont,
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal),
            )
        },
        confirmButton = {
            Button(
                onClick = { onClose(!showDialog) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(text = "OK",
                    fontFamily = BoldFont,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal),
                )
            }
        },
        shape = RoundedCornerShape(30.dp)
    )
}

@Composable
fun jobTag(value : String)
{
    Box(

        modifier = Modifier
            .clip(RoundedCornerShape(40))
            .background(
                color = Color(0xFF40A5FE)
            )

            .padding(5.dp),

    )
    {
        Text(
            text = value,
            fontFamily = normalFont,
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            ),
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun JobCard(content: Vacancy, navController: NavController)
{
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp,
        modifier = Modifier.padding(10.dp),
        onClick = {
            Log.d("Click", "CardExample: Card Click")
            navController.navigate("${Screen.DetailVacancy.name}/${content.vid}")},
        ) {
        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
//                modifier = Modifier.height(70.dp)
            ){
//                Box(
//                    modifier = Modifier
//                        .clip(RoundedCornerShape(2.dp))
//                        .size(70.dp)
//                )
//                {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier.size(70.dp)
                    )
                Spacer(modifier = Modifier.width(20.dp))
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
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = content.title,
                            fontFamily = extraBoldFont,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,
                            ),
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        Spacer(modifier = Modifier.height(7.dp))
                        Text(
                            text = content.companyName,
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
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(id = R.drawable.heart_empty),
                            contentDescription = "Icon"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(startIndent = 1.dp, thickness = 0.2.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(15.dp))
            Row {
                Column {
                    Row{
                        Icon(Icons.Filled.Place, contentDescription = "Icon")
//                        NotCenterNormalTextComponent(value = content.location, size = 14.sp)
                        Text(
                            text = content.location,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 30.dp),
                            fontFamily = normalFont,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                        )
                    }
                    Row{
                        Icon(Icons.Filled.AttachMoney, contentDescription = "Icon")
                        Text(
                            text = "${((content.minSalary * 10).roundToInt() / 10.0f) * 1000} - ${((content.maxSalary * 10).roundToInt() / 10.0f) * 1000} $/month",
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 30.dp),
                            fontFamily = normalFont,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                            color = Color(0xFF2F4AE3)
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState())
                    )
                    {
                        val list = content.programmingLanguage
                        list.forEach{
                            jobTag(value = it)
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                }
            }


        }
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(openDialog: MutableState<Boolean>, title: String = "", disablePast: Boolean = true, onSelected: (Double)->Unit) {
    // Decoupled snackbar host state from scaffold state for demo purposes.
    if (openDialog.value) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
        DatePickerDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onSelected(datePickerState.selectedDateMillis!!.toDouble())
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            },
        ) {
            DatePicker(
                state = datePickerState,
                dateValidator = {
                    if (disablePast) {
                        it >= System.currentTimeMillis()
                    } else {
                        true
                    }
                },
                title = { Text(title) }
            )
        }
    }
}

//Convert date from milisecond to date
fun convertDate(milisecond: Double): String {
    val date = Date(milisecond.toLong())
    val format = SimpleDateFormat("dd/MM/yyyy")
    return format.format(date)
}

@Composable
fun dockedDatePicker(isOpenDialog: MutableState<Boolean>, title: String = "", disiablePast: Boolean = true, onSelected: (Double)->Unit)
{
    val selectedDate = remember { mutableStateOf("Select date") }
    OutlinedTextField(
        readOnly = true,
        textStyle = TextStyle(color = Color.Black),
        value = selectedDate.value,
        onValueChange = {},
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.EditCalendar,
                modifier = Modifier.clickable {
                    isOpenDialog.value = true
                },
                contentDescription = null
            )
        }
    )
    DatePickerDialog(openDialog = isOpenDialog, title = title, disablePast = disiablePast)
    {
        selectedDate.value = convertDate(it)
        onSelected(it)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun jobCardEmployer(content: Vacancy = Vacancy())
{
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp,
        modifier = Modifier.padding(10.dp),
        onClick = { Log.d("Click", "CardExample: Card Click")},
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
                        .size(70.dp)
                )
                {
                    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "")
                }
                Spacer(modifier = Modifier.width(20.dp))
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
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = content.title,
                            fontFamily = extraBoldFont,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,
                            ),
                        )
                        Spacer(modifier = Modifier.height(7.dp))
                        Text(
                            text = content.companyName,
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
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(id = R.drawable.edit),
                            contentDescription = "Icon"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(startIndent = 1.dp, thickness = 0.2.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(15.dp))
            Row {
                Column {
                    Row{
                        Icon(Icons.Filled.Place, contentDescription = "Icon")
                        Text(
                            text = content.location,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 30.dp),
                            fontFamily = normalFont,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                        )
                    }
                    Row{
                        Icon(Icons.Filled.AttachMoney, contentDescription = "Icon")
                        Text(
                            text = "${((content.minSalary * 10).roundToInt() / 10.0f) * 1000} - ${((content.maxSalary * 10).roundToInt() / 10.0f) * 1000}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 30.dp),
                            fontFamily = normalFont,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                            color = Color(0xFF2F4AE3)
                        )
                    }
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState())
                    )
                    {
                        val list = content.programmingLanguage
                        list.forEach{
                            jobTag(value = it)
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun jobcardEmployerApplications(
    content: Vacancy = Vacancy(),
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp,
        modifier = Modifier.padding(10.dp),
        onClick = {
                  navController.navigate("${Screen.ApplicantsList.name}/${content.vid}")
        },
    ) {
        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.height(70.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(2.dp))
                        .size(70.dp)
                )
                {
                    Image( painter = painterResource(id = R.drawable.logo), contentDescription = "", modifier = Modifier.size(70.dp))
                }
                Spacer(modifier = Modifier.width(20.dp))
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
                            text = content.title,
                            fontFamily = extraBoldFont,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,
                            ),
                        )
                        Spacer(modifier = Modifier.height(7.dp))
                        Text(
                            text = content.companyName,
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
                            modifier = Modifier.size(25.dp),
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = "Icon"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(startIndent = 1.dp, thickness = 0.2.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(15.dp))
            Row {
                Column {
                    Row{
                        Icon(Icons.Filled.Place, contentDescription = "Icon")
                        Text(
                            text = content.location,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 30.dp),
                            fontFamily = normalFont,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                        )
                    }
                    Row{
                        Icon(Icons.Filled.AttachMoney, contentDescription = "Icon")
                        Text(
                            text = "${((content.minSalary * 10).roundToInt() / 10.0f) * 1000} - ${((content.maxSalary * 10).roundToInt() / 10.0f) * 1000}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 30.dp),
                            fontFamily = normalFont,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            ),
                            color = Color(0xFF2F4AE3)
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState())
                    )
                    {
                        val list = content.programmingLanguage
                        list.forEach{
                            jobTag(value = it)
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(isLoading:Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Adjust padding as needed
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .alpha(if (isLoading) 1f else 0f),
            color = Color(0xFF2F4AE3)
        )
    }
}

