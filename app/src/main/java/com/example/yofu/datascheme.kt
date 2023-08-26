package com.example.yofu

import android.annotation.SuppressLint
import android.graphics.Bitmap
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference


@SuppressLint("StaticFieldLeak")
val db = Firebase.firestore

val emptyCompanyRef = db.collection("company").document("CID")
val emptyUser = db.collection("user").document("UID")
val emptyVacancy = db.collection("vacancy").document("VID")

data class Vacancy(
    var vid: String = "", //Vacancy Id
    var manager: DocumentReference = emptyCompanyRef,
    var companyName: String = "",
    var title: String = "",
    var minSalary: Float = 0.0f,
    var maxSalary: Float = 20.0f,
    var location: String = "",
    var position: String = "",
    var jobType: String = "",
    var description: String = "",
    var benefit: String = "",
    val programmingLanguage: List<String> = emptyList(),
    var updatedDate: Timestamp = Timestamp(0,0),
    var expiredDate: Timestamp = Timestamp(0,0),
    var isActive: Boolean = false,
) {
    companion object {
//        fun fromFireStore(document: DocumentSnapshot): Vacancy {
//            return Vacancy(
//                expiredDate = document["expiredDate"] as Timestamp ?: Timestamp(0, 0),
//                isActive = document["isActive"] as Boolean ?: true,
//                jobType = document["jobType"] as String ?: "",
//                maxSalary = document["maxSalary"] as Double ?: 0,
//                minSalary = document["minSalary"] as Double ?: 0,
//                title = document["title"] as String ?: "",
//                updatedDate = document["updatedDate"] as Timestamp ?: Timestamp(0, 0),
//                location = document["workLocation"] as String ?: "",
//                position = document["workPosition"] as String ?: "",
//            )
//        }
    }
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$title"
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

data class JobApplication(
    var aid: String = "",
    var vid: DocumentReference = emptyVacancy,
    var uid: DocumentReference = emptyUser,
    var cvDownloadLink: String = "",
    var cvFileName: String = "",
    var newPhone: String = "",
    var newEmail: String = "",
    var status: Boolean? = null
)

data class  User(
    var uid: String = "",
    var cid: DocumentReference = emptyCompanyRef,
    var fullName: String = "",
    var birthDate: Timestamp = Timestamp(0,0),
    var userType: String = "",
    var avt: Bitmap? = null,
    var avtRef: DocumentReference = emptyUser,
    var gender: String = "",
    var phone: String = "",
    var email: String = "",
)

data class Company(
    var cid: String = "",
    var name: String = "",
    var manager: DocumentReference = emptyUser,
    var description: String = "",
    var location: String = "",
    var website: String = "",
    var phone: String = "",
    var email: String = ""
)

data class UserLogin(
    var email: String = "",
    var password: String = ""
)