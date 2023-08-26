package com.example.yofu.jobFinderUI

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.yofu.Vacancy
import com.example.yofu.accountManage.CompanyRepository
import com.example.yofu.jobVacancyManage.VacancyRepository
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailedJobViewModel(): ViewModel(){
    private var vacancyId = "VID"
    private val _state = MutableStateFlow<Vacancy>(Vacancy())
    private val companyInfo = MutableStateFlow<String>("")

    fun setVacancyId(id: String)
    {
        this.vacancyId = id
        Log.d("NavigateDetailedModel", this.vacancyId)
    }

    val state: StateFlow<Vacancy>
        get() = _state

    val company: StateFlow<String>
        get() = companyInfo

    fun loadVacancy(){
        VacancyRepository().fetch(this.vacancyId) { vacancy, e ->
            if (e == null) {
                if (vacancy != null)
                {
                    _state.value = vacancy
                    Log.d("DetailedJobScreen", vacancy.toString())
                    val managerRef = _state.value.manager
                    managerRef.get()
                        .addOnSuccessListener { doc ->
                            if (doc != null) {
                                val manager = doc.toObject<com.example.yofu.User>()
                                if (manager !=null)
                                {
                                    CompanyRepository().fetch(manager.cid) { com, e ->
                                        if (e == null) {
                                            if (com != null) {
                                                companyInfo.value = com.description
                                            }
                                        }
                                    }
                                }
                            }
                            else
                            {
                                Log.d("DetailedJobScreen", "Manager is null")
                            }
                        }
                }
                else
                {
                    Log.d("DetailedJobScreen", "Vacancy is null")
                }
            }
            else
            {
                Log.d("DetailedJobScreen", "Error loading vacancy: $e")
            }
        }
    }

    // Download the CV file of the applicant

}