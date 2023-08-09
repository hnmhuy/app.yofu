package com.example.yofu.accountUI

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yofu.R


val normalFont = FontFamily(
    Font(R.font.raleway_regular, FontWeight.Thin),
)
val extraBoldFont = FontFamily(
    Font(R.font.raleway_black, FontWeight.Black),
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
fun TextFieldComponent(labelValue: String)
{
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = {Text(text = labelValue)},
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
        },

    )
}
@Composable
fun PasswordTextFieldComponent(labelValue: String)
{
    val password = remember {
        mutableStateOf("")
    }
    val passwordVisible = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(50.dp),
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            cursorColor = Color.Black,
            disabledTextColor = Color.Transparent,
            unfocusedBorderColor = Color.LightGray,
            trailingIconColor = Color.LightGray
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        value = password.value,
        onValueChange = {
            password.value = it
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
fun ButtonComponent(value: String)
{
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(50.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(50.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(42.dp)
                .background(
                    color = Color.Blue,
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        )
        {
            val boldFont = FontFamily(
                Font(R.font.raleway_bold, FontWeight.Bold),
            )
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = boldFont,
                color = Color.White
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
        withStyle(style = SpanStyle(color = Color.Blue)){
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