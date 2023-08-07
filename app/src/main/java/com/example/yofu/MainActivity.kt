package com.example.yofu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.yofu.accountManage.AuthenticationProcess
import com.example.yofu.accountManage.UserRepository
import com.google.firebase.Timestamp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val account = UserLogin("minhhuy3323@outlook.com.vn", "1234567890")
        val userInfo = User(fullName = "Huy", birthDate = Timestamp.now(), userType = "Employer")
        val process = AuthenticationProcess()
        val company = Company(name = "Start up moi nhu")
//        process.companySignup(account, userInfo, company) { exception ->
//            if (exception != null) {
//                Log.d("Notification", exception.toString())
//            }
//            else {
//                Log.d("Notification", "Company signup successful")
//            }
//        }
        process.login(account) { user, e ->
            if (e == null)
                Log.d("Notification", user.toString())
            else
                Log.d("Notification", e.toString())
        }
    }
}