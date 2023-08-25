package com.example.yofu.jobVacancyManage

import android.app.Application
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ApplyRepository {
    private val db = Firebase.firestore
    private val application = db.collection("application")

    fun fetchAnApplication(aid: String, onComplete: (Application?, Exception?) -> Unit)
    {
        application.document(aid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val application = document.toObject<Application>()
                    onComplete(application, null)
                } else {
                    onComplete(null, Exception("No such document"))
                }
            }
            .addOnFailureListener { exception ->
                onComplete(null, exception)
            }
    }
}