package com.example.yofu

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.yofu.jobVacancyManage.VacancyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreatedVacanciesViewModel() : ViewModel() {
    private val _vacancies = MutableStateFlow(listOf<Vacancy>())
    val vacancies = _vacancies.asStateFlow()
    private val vacancyRepo = VacancyRepository()

    init {
        vacancyRepo.getVacancyList(
            onComplete = { vacancies, exception ->
                if (exception == null) {
                    _vacancies.value = vacancies
                } else {
                    Log.w("Employer Vacancy", exception)
                }
            }
        )
    }
}