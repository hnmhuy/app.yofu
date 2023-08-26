package com.example.yofu.jobFinderUI

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.yofu.JobApplication
import com.example.yofu.Vacancy
import com.example.yofu.jobVacancyManage.ApplyRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class JFApplicationViewModel: ViewModel() {
    private val _applicationList = MutableStateFlow(listOf<JobApplication>())
    val applicationList: StateFlow<List<JobApplication>>
        get() = _applicationList.asStateFlow()

    private val process = ApplyRepository()

    init {
        getApplicationList()
        Log.d("JFApplicationViewModel", "init")
    }

    fun getApplicationList() {
        process.getApplicationListOfJobFinder { jobApplications, exception ->
            if (exception == null) {
                if (jobApplications != null) {
                    _applicationList.value = jobApplications
                    // Sort by date
                    _applicationList.value = _applicationList.value.sortedByDescending { it.applyDate }
                }
            } else {
                println(exception)
            }
        }
    }
}