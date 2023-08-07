package com.example.yofu.accountManage

import android.util.Log
import com.example.yofu.User
import com.example.yofu.UserLogin
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

const val LOGIN = "login"
const val SIGNUP = "signup"

class AuthenticationProcess {
    fun login(account: UserLogin, onComplete: (User?, Exception?) -> Unit) {
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(account.email, account.password)
            .addOnSuccessListener {
                if (auth.currentUser?.isEmailVerified == true) {
                    Log.d(LOGIN,"Login successful: UID = ${auth.currentUser?.uid.toString()}")
                    val process = UserRepository()
                    process.fetch(auth.currentUser?.uid.toString()) { user, exception ->
                        onComplete(user, exception)
                    }
                }
                else {
                    Log.d(LOGIN, "This account hasn't verified the email")
                    onComplete(null, Exception("This account hasn't verified the email"))
                }
            }
    }

    fun sigup(newAccount: UserLogin, userData: User, onComplete: (Exception?) -> Unit) {
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(newAccount.email, newAccount.password)
            .addOnSuccessListener {
                Log.d(SIGNUP, "Create an account successful: Email: ${newAccount.email}")
                auth.currentUser?.sendEmailVerification()
                    ?.addOnSuccessListener {
                        Log.d(SIGNUP, "Sent the verify email")
                    }
                    ?.addOnFailureListener { e->
                        Log.d(SIGNUP, e.toString())
                    }
                userData.uid = auth.currentUser?.uid.toString()
                val process = UserRepository()
                process.update(userData) { exception ->
                    onComplete(exception)
                }
            }
            .addOnFailureListener() { e ->
                Log.d(SIGNUP, e.toString())
                onComplete(e)
            }
    }
}