package com.example.yofu

import android.graphics.Bitmap
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference


data class Vacancy(
    val vid: String, //Vacancy Id
    val manager: DocumentReference,
    val title: String,
    val minSalary: Double,
    val maxSalary: Double,
    val location: String,
    val position: String,
    val jobType: String,
    val updatedDate: Timestamp,
    val expiredDate: Timestamp,
    val isActive: Boolean,
)

data class JobApplication(
    val aid: String,
    val vid: DocumentReference,
    val uid: DocumentReference,
    val cvRef: DocumentReference,
    val status: String
)

data class  User(
    val uid: String,
    val cid: DocumentReference,
    val fullName: String,
    val birthDate: Timestamp,
    val userType: String,
    val avt: Bitmap,
    val avtRef: DocumentReference,
    val gender: String,
    val phone: String
)

data class Company(
    val cid: String,
    val name: String,
    val manager: DocumentReference,
    val description: String,
    val location: String,
    val website: String,
    val phone: String,
    val email: String
)

data class UserLogin(
    val email: String,
    val password: String
)

fun map(data: Vacancy): HashMap<String, Any> {
    return hashMapOf(
        "manager" to data.manager,
        "title" to data.title,
        "minSalary" to data.minSalary,
        "maxSalary" to data.maxSalary,
        "location" to data.location,
        "position" to data.position,
        "jopType" to data.jobType,
        "updatedDate" to data.updatedDate,
        "expiredDate" to data.expiredDate,
        "isActive" to data.isActive
    )
}

fun map(data: JobApplication): HashMap<String, Any>
{
    return hashMapOf(
        "vid" to data.vid,
        "uid" to data.vid,
        "cvRef" to data.cvRef,
        "status" to data.status
    )
}

fun map(data: User): HashMap<String, Any>
{
    return hashMapOf(
        "cid" to data.cid,
        "fullName" to data.fullName,
        "birthDate" to data.birthDate,
        "userType" to data.userType,
        "avtRef" to data.avtRef,
        "gender" to data.gender,
        "phone" to data.phone
    )
}

fun map(data: Company): HashMap<String, Any>
{
    return hashMapOf(
        "name" to data.name,
        "manager" to data.manager,
        "description" to data.description,
        "location" to data.location,
        "website" to data.website,
        "phone" to data.phone,
        "email" to data.email
    )
}