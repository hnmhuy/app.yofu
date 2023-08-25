package com.example.yofu.jobFinderUI

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ApplyScreenViewModel: ViewModel() {
    private val _vid = MutableStateFlow<String>("")
    val vid = _vid.asStateFlow()

    private val _pdfURI = MutableStateFlow<Uri>(Uri.EMPTY)
    val pdfURI = _pdfURI.asStateFlow()

    fun setVid(curVid: String) {
        _vid.value = curVid
    }

    fun setPdfURI(uri: Uri) {
        _pdfURI.value = uri
    }
    fun uploadPDFtoFirebase() {
        val uri = pdfURI.value

        val filename = "cv_${System.currentTimeMillis()}.pdf"

        val storageRef = Firebase.storage.reference.child("pdf/${filename}")

        Log.d("PDF uploading", uri.toString())

        val pdfRef = storageRef.putFile(uri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener {
                Log.d("PDF uploading", "Successfully get download link")
                val downloadUrl = it.toString()
                val uid = Firebase.auth.currentUser?.uid
                val data = hashMapOf(
                    "candidate" to uid,
                    "cvRef" to downloadUrl,
                    "status" to "Waiting",
                    "vid" to vid.value,
                )

                val db = Firebase.firestore
                db.collection("application").document().set(data).addOnSuccessListener {
                    Log.d("PDF Uploading", "Successfully add new CV")
                }.addOnFailureListener {
                    Log.d("PDF Uploading", "Failed to add new CV")
                }

            }.addOnFailureListener {
                Log.d("PDF Uploading", "Failed to get download link")
            }
        }.addOnFailureListener {
            Log.d("PDF Uploading", "can not add pdf to firebase storage")
        }
    }
}