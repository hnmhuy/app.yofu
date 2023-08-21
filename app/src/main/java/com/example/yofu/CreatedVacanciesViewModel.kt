package com.example.yofu

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.yofu.jobVacancyManage.VacancyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreatedVacanciesViewModel() : ViewModel() {
    private val _vacancies = MutableStateFlow(listOf<Vacancy>())
    val vacancies: StateFlow<List<Vacancy>>
        get() = _vacancies.asStateFlow()
    private val vacancyRepo = VacancyRepository()
    fun loadVacancies(){
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

    init {
//        vacancyRepo.getVacancyList(
//            onComplete = { vacancies, exception ->
//                if (exception == null) {
//                    _vacancies.value = vacancies
//                } else {
//                    Log.w("Employer Vacancy", exception)
//                }
//            }
//        )
        loadVacancies()
    }
}