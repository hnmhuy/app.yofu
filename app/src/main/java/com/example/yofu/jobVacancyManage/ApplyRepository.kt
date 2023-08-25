package com.example.yofu.jobVacancyManage

import android.net.Uri
import android.util.Log
import com.example.yofu.JobApplication
import com.example.yofu.Vacancy
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference

class ApplyRepository {
    private val db = Firebase.firestore
    private val application = db.collection("application")
    private val currentUser = Firebase.auth.currentUser

    fun fetchAnApplication(aid: String, onComplete: (JobApplication?, Exception?) -> Unit){
        application.document(aid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val application = document.toObject<JobApplication>()
                    onComplete(application, null)
                } else {
                    onComplete(null, Exception("No such document"))
                }
            }
            .addOnFailureListener { exception ->
                onComplete(null, exception)
            }
    }

    fun apply(vid: String, newEmail: String = "", newPhone: String = "", cvRef: String,onComplete: (JobApplication?, Exception?) -> Unit)
    {
        if (currentUser == null)
        {
            onComplete(null, Exception("No user logged in"))
        }
        else
        {
            val newApplication = JobApplication(
                vid = db.collection("vacancy").document(vid),
                uid = db.collection("user").document(currentUser.uid),
                newEmail = newEmail,
                newPhone = newPhone,
                cvRef = cvRef
            )
            application.add(newApplication)
                .addOnSuccessListener { documentReference ->
                    fetchAnApplication(documentReference.id) {a, exception ->
                        if(exception == null)
                        {
                            a?.aid = documentReference.id
                            application.document(documentReference.id)
                                .set(a!!)
                                .addOnSuccessListener {
                                    onComplete(a, null)
                                }
                                .addOnFailureListener { e ->
                                    onComplete(null, e)
                                }
                        }
                        else
                        {
                            onComplete(null, exception)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    onComplete(null, e)
                }
        }

    }

    fun acceptApplication(aid: String, onComplete: (String) -> Unit) {
        application.document(aid)
            .update("status", true)
            .addOnSuccessListener {
                onComplete("Successfully accept application")
            }
            .addOnFailureListener {
                onComplete("Failed to accept application")
            }
    }

    fun rejectApplication(aid: String, onComplete: (String) -> Unit) {
        application.document(aid)
            .update("status", false)
            .addOnSuccessListener {
                onComplete("Successfully reject application")
            }
            .addOnFailureListener {
                onComplete("Failed to reject application")
            }
    }

    fun isApplied(vid: String, onComplete: (Boolean) -> Unit) {
        if (currentUser == null) {
            onComplete(false)
        } else {
            application.whereEqualTo("uid", db.collection("user").document(currentUser.uid)).whereEqualTo("vid", db.collection("vacancy").document(vid))
                .whereEqualTo("vid", db.collection("vacancy").document(vid))
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        Log.d("isApplied", "No such document")
                        onComplete(false)
                    } else {
                        Log.d("isApplied", "Successfully get document")
                        onComplete(true)
                    }
                }
                .addOnFailureListener {
                    Log.d("isApplied", "Failed to get document")
                    onComplete(false)
                }
        }
    }

    fun getApplicationListOfJobFinder(
        onComplete: (List<JobApplication>?, Exception?) -> Unit
    ) {
        if (currentUser == null) {
            onComplete(null, Exception("No user logged in"))
        } else {
            application.whereEqualTo("uid", db.collection("user").document(currentUser.uid))
                .get()
                .addOnSuccessListener { documents ->
                    val applicationList = mutableListOf<JobApplication>()
                    for (document in documents) {
                        val application = document.toObject<JobApplication>()
                        application.aid = document.id
                        applicationList.add(application)
                        Log.d("getApplicationListOfJobFinder", "${document.id} => ${document.data}")
                    }
                    Log.d("getApplicationListOfJobFinder", "Successfully get application list")
                    onComplete(applicationList, null)
                }
                .addOnFailureListener { exception ->
                    Log.d("getApplicationListOfJobFinder", "Failed to get application list")
                    onComplete(null, exception)
                }
        }
    }

    fun getApplicationOfAVacancy(
        vid: String,
        onComplete: (List<JobApplication>, Exception?) -> Unit
    )
    {
        application.whereEqualTo("vid", db.collection("vacancy").document(vid))
            .get()
            .addOnSuccessListener { documents ->
                val applicationList = mutableListOf<JobApplication>()
                for (document in documents) {
                    val application = document.toObject<JobApplication>()
                    application.aid = document.id
                    applicationList.add(application)
                    Log.d("getApplicationOfAVacancy", "${document.id} => ${document.data}")
                }
                Log.d("getApplicationOfAVacancy", "Successfully get application list")
                onComplete(applicationList, null)
            }
            .addOnFailureListener { exception ->
                Log.d("getApplicationOfAVacancy", "Failed to get application list")
                onComplete(mutableListOf(), exception)
            }
    }


}