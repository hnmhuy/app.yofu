package com.example.yofu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent

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

