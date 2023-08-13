package com.example.yofu

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.navigation.NavController

@Composable
fun HomepageScreen(
    navController: NavController
) {
    Text(
        text = "homepage"
    )
}
