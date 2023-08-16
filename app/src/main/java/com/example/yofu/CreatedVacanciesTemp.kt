package com.example.yofu

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun CreatedVacanciesTemp(
    navController: NavController,
    createdVacanciesViewModel: CreatedVacanciesViewModel = viewModel<CreatedVacanciesViewModel>()
) {
    val vacancies = createdVacanciesViewModel.vacancies.collectAsState().value

    LazyColumn() {
        items(vacancies) { vacancy ->
            Text(vacancy.title)
        }
    }
}