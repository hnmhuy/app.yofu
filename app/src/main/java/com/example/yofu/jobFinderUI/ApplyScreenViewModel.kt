package com.example.yofu.jobFinderUI

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.yofu.jobVacancyManage.ApplyRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ApplyScreenViewModel: ViewModel() {
    private val _vid = MutableStateFlow<String>("")
    val vid = _vid.asStateFlow()

    private val _pdfURI = MutableStateFlow<Uri>(Uri.EMPTY)
    val pdfURI = _pdfURI.asStateFlow()

    private val _isUploading = MutableStateFlow(false)
    val isUploading = _isUploading.asStateFlow()

    private val _newEmail = MutableStateFlow("")
    val newEmail = _newEmail.asStateFlow()

    private val _newPhone = MutableStateFlow("")
    val newPhone = _newPhone.asStateFlow()

    fun setVid(curVid: String) {
        _vid.value = curVid
    }

    fun setPdfURI(uri: Uri) {
        _pdfURI.value = uri
    }

    fun setNewEmail(email: String) {
        _newEmail.value = email
    }

    fun setNewPassword(password: String) {
        _newPhone.value = password
    }

    fun uploadPDFtoFirebase(onProcess: (percent: Float) -> Unit, onComplete: (Boolean, String, String) -> Unit) {
        val uri = pdfURI.value

        val filename = "cv_${System.currentTimeMillis()}.pdf"

        val storageRef = Firebase.storage.reference.child("pdf/${filename}")

        Log.d("PDF uploading", uri.toString())

        val pdfRef = storageRef.putFile(uri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener {
                Log.d("PDF uploading", "Successfully get download link")
                val downloadUrl = it.toString()
                onComplete(true, downloadUrl, filename)

            }.addOnFailureListener {
                onComplete(false, "", filename)
                Log.d("PDF Uploading", "Failed to get download link")
            }
        }.addOnFailureListener {
            onComplete(false, "", filename)
            Log.d("PDF Uploading", "can not add pdf to firebase storage")
        }
        .addOnProgressListener { taskSnapshot ->
            val progress = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
            onProcess(progress.toFloat())
        }
    }

    private fun verifyCVExsist(): Boolean
    {
        return pdfURI.value != Uri.EMPTY
    }

    fun applyJob(onProcess: (percent: Float) -> Unit, onComplete: (Boolean, String) -> Unit) {
        val email = newEmail.value
        val password = newPhone.value
        if (verifyCVExsist()) {
            uploadPDFtoFirebase(
                onProcess = {
                    onProcess(it)
                }
            ) { success, link, fileName ->
                if (success) {
                    ApplyRepository().apply(vid.value, email, password, link, fileName) { _, exception ->
                        if (exception == null) {
                            onComplete(true, "Apply successfully")
                        } else {
                            onComplete(false, exception.message.toString())
                        }
                    }
                } else {
                    onComplete(false, "Can not upload CV to firebase storage")
                }
            }
        }
        else
        {
            onComplete(false, "Please choose your CV")
        }
    }
}