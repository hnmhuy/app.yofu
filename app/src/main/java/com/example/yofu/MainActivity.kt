package com.example.yofu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import com.example.yofu.accountManage.AuthenticationProcess
import com.example.yofu.accountUI.AboutAccountCompanyScreen
import com.example.yofu.accountUI.ChooseRoleScreen
import com.example.yofu.accountUI.CompanyCreateAccountScreen
import com.example.yofu.accountUI.CreateAccountScreen
import com.example.yofu.accountUI.LoginScreen
import com.example.yofu.jobVacancyManage.VacancyRepository
import com.google.firebase.Timestamp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val account = UserLogin("minhhuu3323@gmail.com", "1234567890")
        //val userInfo = User(fullName = "Huy", birthDate = Timestamp.now(), userType = "JobFinder")
        val process = AuthenticationProcess()
        process.login(account) { user, exception ->
            if (exception != null) {
                Log.d("Notification", exception.toString())
            } else {
                Log.d("Notification", user.toString())
                // Test create vacancy
//                for (i in 1..10) {
//                    val vacancy = Vacancy(
//                        title = "Android Developer$i",
//                        minSalary = 1000.0,
//                        maxSalary = 2000.0,
//                        location = "HCM",
//                        position = "Android Developer",
//                        jobType = "Full-time",
//                        updatedDate = Timestamp.now(),
//                        expiredDate = Timestamp.now(),
//                        isActive = true
//                    )
//                    val vacancyRepository = VacancyRepository()
//                    vacancyRepository.create(vacancy)  { vacancy, exception ->
//                        if (exception != null) {
//                            Log.d("Notification", exception.toString())
//                        } else {
//                            Log.d("Notification", vacancy.toString())
//                        }
//                    }
                val vacancyRepository = VacancyRepository()
                vacancyRepository.getVacancyList { vacancies, exception ->
                    if (exception != null) {
                        Log.d("Notification", exception.toString())
                    } else {
                        // Log all vacancies
                        for (vacancy in vacancies) {
                            Log.d("Notification", vacancy.toString())
                        }
                    }
                }
            }
        }
    }
}