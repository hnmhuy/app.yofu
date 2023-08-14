package com.example.yofu.employerUI

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.yofu.Vacancy
import com.example.yofu.jobVacancyManage.VacancyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateVacancyViewModel : ViewModel() {
    private val _state = MutableStateFlow<Vacancy>(Vacancy())
    private val _program = MutableStateFlow<MutableList<Boolean>>(MutableList(12) { false })
    private val programmingLanguage = listOf<String>("Java Script", "Java", "Kotlin", "PHP", "C#", "C/C++", "HTML", "CSS", "Matlab", "TypeScript", "SQL", "Order");
    val process = VacancyRepository()

    val state: StateFlow<Vacancy>
        get() = _state

    fun setTitle(newTitle: String)
    {
        _state.value = _state.value.copy(
            title = newTitle
        )
        Log.d("CreateVacancyViewModel", _state.value.title)
    }

    fun setLocation(newLocation: String)
    {
        _state.value = _state.value.copy(
            location = newLocation
        )
        Log.d("CreateVacancyViewModel", _state.value.location)
    }

    fun setSalary(min: Float, max: Float) {
        _state.value = _state.value.copy(
            minSalary = min,
            maxSalary = max
        )
        Log.d("CreateVacancyViewModel", _state.value.minSalary.toString())
        Log.d("CreateVacancyViewModel", _state.value.maxSalary.toString())
    }

    fun setPosition(newPosition: String)
    {
        _state.value = _state.value.copy(
            position = newPosition
        )
        Log.d("CreateVacancyViewModel", _state.value.position)
    }

    fun setJobType(newJobType: String)
    {
        _state.value = _state.value.copy(
            jobType = newJobType
        )
        Log.d("CreateVacancyViewModel", _state.value.jobType)
    }

    fun updateProgram(index: Int)
    {
       _program.value[index] = !_program.value[index]
        Log.d("CreateVacancyViewModel", _program.value.toString())
    }

    fun setDescription(newDescription: String)
    {
        _state.value = _state.value.copy(
            description = newDescription
        )
        Log.d("CreateVacancyViewModel", _state.value.description)
    }

    private fun getProgram(): List<String>
    {
        val result = mutableListOf<String>()
        for (i in 0..11)
        {
            if (_program.value[i])
            {
                result.add(programmingLanguage[i])
            }
        }
        return result
    }

    fun createVacancy(navigate: (Exception?)-> Unit)
    {
        var message = ""
        _state.value = _state.value.copy(
            programmingLanguage = getProgram()
        )
        process.create(_state.value) { vacancy, e ->
            if (e != null){
                Log.d("CreateVacancyViewModel", e.toString())
                navigate(e)
            }
            else {
                Log.d("CreateVacancyViewModel", vacancy.toString())
                navigate(e)
            }
        }
    }
}

