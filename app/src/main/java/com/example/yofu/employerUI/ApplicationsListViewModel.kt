package com.example.yofu.employerUI

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.yofu.JobApplication
import com.example.yofu.User
import com.example.yofu.Vacancy
import com.example.yofu.accountManage.UserRepository
import com.example.yofu.emptyCompanyRef
import com.example.yofu.emptyUser
import com.example.yofu.jobVacancyManage.ApplyRepository
import com.example.yofu.jobVacancyManage.VacancyRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.MutableStateFlow

class ApplicationsListViewModel: ViewModel() {
    private val _vacancyList: MutableStateFlow<List<Vacancy>> = MutableStateFlow(listOf<Vacancy>())

    private val _applicationsList: MutableStateFlow<List<JobApplication>> = MutableStateFlow(listOf<JobApplication>())

    private val _vacancyInfo = MutableStateFlow(Vacancy())

    private val _userInfo = MutableStateFlow(User())

    private val _applicationInfo = MutableStateFlow(JobApplication())

    val applicationInfo: MutableStateFlow<JobApplication>
        get() = _applicationInfo

    val userInfo: MutableStateFlow<User>
        get() = _userInfo

    val vacancyInfo: MutableStateFlow<Vacancy>
        get() = _vacancyInfo

    val applicationsList: MutableStateFlow<List<JobApplication>>
        get() = _applicationsList

    val vacancyList: MutableStateFlow<List<Vacancy>>
        get() = _vacancyList

    fun loadVacancies() {
        VacancyRepository().getVacancyList { vacancies, exception ->
            if (exception == null) {
                _vacancyList.value = vacancies
            } else {
                Log.w("ApplicationList", exception)
            }
        }
    }

    fun loadVacancyInfo(vid: String) {
        VacancyRepository().fetch(vid) { vacancy, exception ->
            if (exception == null) {
                _vacancyInfo.value = vacancy!!
            } else {
                Log.w("ApplicationList", exception)
            }
        }
    }

    fun loadApplications(vid: String) {
        ApplyRepository().getApplicationOfAVacancy(vid) { applications, exception ->
            if (exception == null) {
                _applicationsList.value = applications
            } else {
                Log.w("ApplicationList", exception)
            }
        }
    }

    fun getDetailApplication(aid: String) {
        ApplyRepository().fetchAnApplication(aid) { application, exception ->
            if (exception == null) {
                _applicationInfo.value = _applicationInfo.value.copy(
                    aid = application!!.aid,
                    vid = application.vid,
                    uid = application.uid,
                    cvRef = application.cvRef,
                    newEmail = application.newEmail,
                    newPhone = application.newPhone,
                    status = application.status
                )
                Log.d("ApplicationList", _applicationInfo.value.toString())
                // Get user info
                UserRepository().fetch(application.uid.id) { user, exception ->
                    if (exception == null) {
                        _userInfo.value = _userInfo.value.copy(
                            uid = user!!.uid,
                            cid = user.cid,
                            fullName = user.fullName,
                            birthDate = user.birthDate,
                            avt = user.avt,
                            gender = user.gender,
                            phone = user.phone,
                            email = user.email
                        )
                        Log.d("ApplicationList", _userInfo.value.toString())
                    } else {
                        Log.w("ApplicationList", exception)
                    }
                }
            } else {
                Log.w("ApplicationList", exception)
            }
        }
    }

    fun calDatePosted(): String{
        val date = vacancyInfo.value.updatedDate
        val now = Timestamp.now()
        val diff = now.seconds - date.seconds
        val day = diff / 86400
        val hour = (diff % 86400) / 3600
        val minute = ((diff % 86400) % 3600) / 60
        val second = ((diff % 86400) % 3600) % 60
        return when {
            day > 0 -> {
                "$day days ago"
            }
            hour > 0 -> {
                "$hour hours ago"
            }
            minute > 0 -> {
                "$minute minutes ago"
            }
            else -> {
                "$second seconds ago"
            }
        }
    }

}