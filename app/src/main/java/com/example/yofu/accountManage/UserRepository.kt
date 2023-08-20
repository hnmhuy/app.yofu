package com.example.yofu.accountManage

import android.util.Log
import com.example.yofu.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

const val  DB_USER = "user"
const val DBU = "user database"
class UserRepository() {
    fun fetch(uid: String, onComplete: (User?, Exception?) -> Unit) {
        val db = Firebase.firestore
        if(uid == "")
        {
            onComplete(null, Exception("UID empty"))
            return
        }
        db.collection(DB_USER).document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val user = document.toObject<User>()
                    Log.d(DBU, "DocumentSnapshot data: ${document.id}")
                    Log.d(DBU, user.toString())
                    onComplete(user, null)
                } else {
                    Log.d(DBU, "No such document")
                    onComplete(null, Exception("No such document"))
                }
            }
            .addOnFailureListener { exception ->
                Log.d(DBU, "get failed with ", exception)
                onComplete(null, exception)
            }
    }
    fun update(userData: User, onComplete: (Exception?) -> Unit){
        val db = Firebase.firestore
        //Checking uid is not empty
        if (userData.uid == "") {
            onComplete(Exception("UID is empty"))
            return
        }
        db.collection(DB_USER).document(userData.uid)
            .set(userData)
            .addOnSuccessListener {
                Log.d(DBU, "DocumentSnapshot successfully written!")
                onComplete(null)
            }
            .addOnFailureListener { exception ->
                Log.w(DBU, "Error writing document", exception)
                onComplete(exception)
            }
    }
}
