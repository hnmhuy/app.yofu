package com.example.yofu.jobVacancyManage

import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.compose.ui.platform.LocalContext
import com.example.yofu.JobApplication
import com.example.yofu.User
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.File

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

    fun apply(vid: String, newEmail: String = "", newPhone: String = "", link: String, ref: String, onComplete: (JobApplication?, Exception?) -> Unit)
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
                cvDownloadLink = link,
                cvFileName = ref,
                status = null,
                // Set time is now
                applyDate = Timestamp.now()
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
                        Log.d("ApplyRepository", "${document.id} => ${document.data}")
                    }
                    Log.d("ApplyRepository", "Successfully get application list")
                    onComplete(applicationList, null)
                }
                .addOnFailureListener { exception ->
                    Log.d("ApplyRepository", "Failed to get application list")
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
                    Log.d("ApplyRepository", "${document.id} => ${document.data}")
                }
                Log.d("ApplyRepository", "Successfully get application list")
                onComplete(applicationList, null)
            }
            .addOnFailureListener { exception ->
                Log.d("ApplyRepository", "Failed to get application list")
                onComplete(mutableListOf(), exception)
            }
    }

    fun downloadCV(applicationData: JobApplication, userInfo: User, context: Context, onComplete: (File, Boolean) -> Unit, onProgress: (Int) -> Unit)
    {


        // Get Vacancy info
        VacancyRepository().fetch(applicationData.vid) { vacancy, e ->
            if (e == null)
            {
                val localFile = File.createTempFile("${vacancy!!.title}-${userInfo.email}", "pdf")
                val storageRef = Firebase.storage.reference.child("pdf/${applicationData.cvFileName}")
                storageRef.getFile(localFile)
                    .addOnProgressListener {
                        val progress = (100.0 * it.bytesTransferred) / it.totalByteCount
                        onProgress(progress.toInt())
                    }
                    .addOnSuccessListener {
                        Log.d("DownloadCV", "Successfully download CV")
                        // Save this file into local storage
                        val file = File(context.getExternalFilesDir(null), "downloadCV/${vacancy.title}/${vacancy.title}-${userInfo.email}.pdf")
                        if (file.exists()) {
                            onComplete(file, true)
                        }
                        // Create folder if not exist
                        file.parentFile.mkdirs()
                        try {
                            file.writeBytes(localFile.readBytes())
                            Log.d("DownloadCV", "Successfully save CV")
                            // Log the path to file
                            Log.d("DownloadCV", file.absolutePath)
                            onComplete(file, true)
                        } catch (e: Exception) {
                            Log.d("DownloadCV", "Failed to save CV", )
                            onComplete(File(""), false)
                        }
                    }
                    .addOnFailureListener {
                        Log.d("DownloadCV", "Failed to download CV")
                        onComplete(File(""), false)
                    }
            }
        }
    }

    fun getMimeType(file: File): String?
    {
        val extension = file.extension
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }



}