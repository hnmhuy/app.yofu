package com.example.yofu.employerUI

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.yofu.Vacancy
import com.example.yofu.accountManage.UserRepository
import com.example.yofu.jobVacancyManage.VacancyRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.type.DateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class CreateVacancyViewModel : ViewModel() {
    private val _state = MutableStateFlow<Vacancy>(Vacancy(updatedDate = Timestamp.now(), expiredDate = Timestamp.now()))
    private val _program = MutableStateFlow<MutableList<Boolean>>(MutableList(12) { false })
    private val programmingLanguage = listOf<String>("Java Script", "Java", "Kotlin", "PHP", "C#", "C/C++", "HTML", "CSS", "Matlab", "TypeScript", "SQL", "Other");
    val process = VacancyRepository()

    val state: StateFlow<Vacancy>
        get() = _state

    fun setTitle(newTitle: String)
    {
        _state.value = _state.value.copy(
            title = newTitle
        )
    }

    fun setLocation(newLocation: String)
    {
        _state.value = _state.value.copy(
            location = newLocation
        )
    }

    fun setSalary(min: Float, max: Float) {
        _state.value = _state.value.copy(
            minSalary = min,
            maxSalary = max
        )
    }

    fun setPosition(newPosition: String)
    {
        _state.value = _state.value.copy(
            position = newPosition
        )
    }

    fun setJobType(newJobType: String)
    {
        _state.value = _state.value.copy(
            jobType = newJobType
        )
    }

    fun updateProgram(index: Int)
    {
       _program.value[index] = !_program.value[index]
        _state.value = _state.value.copy(
            programmingLanguage = getProgram()
        )
    }

    fun setDescription(newDescription: String)
    {
        _state.value = _state.value.copy(
            description = newDescription
        )
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

    fun setDueDate(newDateDue: Double)
    {
        _state.value = _state.value.copy(
            expiredDate = Timestamp((newDateDue/1000).toLong(), ((newDateDue % 1000) * 1000).toInt())
        )
    }

    fun setBenefit(newBenefit: String)
    {
        _state.value = _state.value.copy(
            benefit = newBenefit
        )
    }


    fun verify(onComplete: (String) -> Unit): Boolean
    {
        // Check if title is empty
        if (_state.value.title == "")
        {
            onComplete("Please enter title")
            return false
        }
        // Check if location is empty
        if (_state.value.location == "")
        {
            onComplete("Please enter location")
            return false
        }
        // Check if position is empty
        if (_state.value.position == "")
        {
            onComplete("Please enter position")
            return false
        }
        // Check if job type is empty
        if (_state.value.jobType == "")
        {
            onComplete("Please enter job type")
            return false
        }
        // Check if description is empty
        if (_state.value.description == "")
        {
            onComplete("Please enter description")
            return false
        }
        // Check if programming language is empty
        if (_state.value.programmingLanguage.isEmpty())
        {
            onComplete("Please choose at least one programming language")
            return false
        }
        // Check if due date is empty
        if (_state.value.expiredDate == Timestamp(0,0))
        {
            onComplete("Please choose due date")
            return false
        }
        // Check position
        if (_state.value.position == "")
        {
            onComplete("Please choose position")
            return false
        }
        return true
    }

    fun createVacancy(navigate: (Exception?)-> Unit)
    {
        var message = ""
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

