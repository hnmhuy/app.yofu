package com.example.yofu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.yofu.accountManage.AuthenticationProcess
import com.example.yofu.accountManage.UserRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val account = UserLogin("minhhuu3323@gmail.com", "1234567890")
        //val userInfo = User(fullName = "Huy", birthDate = Timestamp.now(), userType = "JobFinder")
        val process = AuthenticationProcess()
        process.login(account) { user, e ->
            if (e == null)
            {
                Log.d("NOTIFICATION", "Login successfully ${user?.fullName}")
                val currentUser: User = user ?: User()
                currentUser.fullName = "Thử đổi qua Tiếng Việt coi có bị lỗi hong :)"
                val userProcess = UserRepository()
                userProcess.update(currentUser) {
                    if (it != null)
                        Log.d("NOTIFICATION", "Update account successfully")
                    else
                        Log.d("NOTIFICATION", it.toString())
                }
            }
            else {
                Log.d("NOTIFICATION", e.toString())
            }
        }

    }
}