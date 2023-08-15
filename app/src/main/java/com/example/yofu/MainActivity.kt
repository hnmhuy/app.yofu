package com.example.yofu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.yofu.accountManage.AuthenticationProcess
import com.example.yofu.accountUI.AboutAccountCompanyScreen
import com.example.yofu.accountUI.ChooseRoleScreen
import com.example.yofu.accountUI.CompanyCreateAccountScreen
import com.example.yofu.accountUI.CreateAccountScreen
import com.example.yofu.accountUI.LoginScreen
import com.example.yofu.employerUI.DetailedJobScreen
import com.example.yofu.employerUI.ProfileScreen
import com.example.yofu.jobVacancyManage.VacancyRepository
import com.google.firebase.Timestamp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContent{
            Navigation()
            //ProfileScreen()
        }
//        val account = UserLogin("minhhuu3323@gmail.com", "1234567890")
        //val userInfo = User(fullName = "Huy", birthDate = Timestamp.now(), userType = "JobFinder")
//        val process = AuthenticationProcess()
//        process.login(account) { user, exception ->
//            if (exception != null) {
//                Log.d("Notification", exception.toString())
//            } else {
//                Log.d("Notification", user.toString())
//            }
    }
}

