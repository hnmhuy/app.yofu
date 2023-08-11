package com.example.yofu.accountManage

import android.util.Log
import com.example.yofu.Company
import com.example.yofu.User
import com.example.yofu.UserLogin
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
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
            .addOnFailureListener { e -> onComplete(null, e)}
    }

    fun signup(newAccount: UserLogin, userData: User, onComplete: (User?, Exception?) -> Unit) {
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
                    onComplete(userData, exception)
                }
            }
            .addOnFailureListener { e ->
                Log.d(SIGNUP, e.toString())
                onComplete(null, e)
            }
    }

    fun companySignup(newAccout: UserLogin, userInfo: User, newCompany: Company,
                      onComplete: (Exception?) -> Unit) {
        this.signup(newAccout, userInfo) { user, e ->
            if(e==null) {
                val db = Firebase.firestore
                newCompany.manager = db.collection("user").document(user?.uid.toString())
                val companyProcess = CompanyRepository()
                companyProcess.create(newCompany) { dataCompany, exception ->
                    if (exception == null) {
                        Log.d(SIGNUP, "Company signup successful")
                        // Update user data
                        user?.cid = db.collection("company").document(dataCompany.cid)
                        val userProcess = UserRepository()
                        userProcess.update(user!!) {
                            onComplete(it)
                        }
                    } else {
                        Log.d(SIGNUP, exception.toString())
                        // Delete the user account
                        val auth = Firebase.auth
                        auth.currentUser?.delete()
                            ?.addOnSuccessListener { Log.d(SIGNUP, "Delete the user account") }
                            ?.addOnFailureListener { e -> Log.d(SIGNUP, e.toString())}
                        onComplete(exception)
                    }
                }
            }
        }
    }
}