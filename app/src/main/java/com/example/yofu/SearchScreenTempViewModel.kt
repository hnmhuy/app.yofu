package com.example.yofu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yofu.jobVacancyManage.VacancyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn

class SearchScreenTempViewModel() : ViewModel() {
    private val vacancyRepo = VacancyRepository()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _vacancies = MutableStateFlow(listOf<Vacancy>())
    val vacancies = searchText
        .debounce(500L)  // if use does not type anything after 0.5s -> do the code below
        .combine(_vacancies) { text, vacancies ->
            if (text.isBlank()) {
                vacancies
            } else {
                    vacancies.filter {
                        it.doesMatchSearchQuery(text)
                    }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _vacancies.value
        )

    init {
        vacancyRepo.getAllVacancies { vacancies ->
            _vacancies.value = vacancies
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}