package com.example.yofu.jobVacancyManage

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val DB_VACANCY = "vacancy"
const val DBV = "vacancy database"

class FormField() {

    private val db = Firebase.firestore
    private val utilites = db.collection("utilities")
    fun getProvices(onComplete: (MutableList<String>, Exception?) -> Unit) {
        utilites.document("provinces")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val provinces = document.data?.get("provinces") as MutableList<String>
                    onComplete(provinces, null)
                } else {
                    onComplete(mutableListOf(), Exception("No such document"))
                }
            }
            .addOnFailureListener { exception ->
                onComplete(mutableListOf(), exception)
            }
    }

    fun getPosition(onComplete: (MutableList<String>, Exception?) -> Unit) {
        utilites.document("position")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val position = (document.data?.get("position") as MutableList<String>).also {
                        onComplete(it, null)
                    }
                } else {
                    onComplete(mutableListOf(), Exception("No such document"))
                }
            }
            .addOnFailureListener { exception ->
                onComplete(mutableListOf(), exception)
            }
    }

    fun getProgrammingLanguage(onComplete: (MutableList<String>, Exception?) -> Unit) {
        utilites.document("programmingLanguage")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val programmingLanguage = (document.data?.get("programmingLanguage") as MutableList<String>).also {
                        onComplete(it, null)
                    }
                } else {
                    onComplete(mutableListOf(), Exception("No such document"))
                }
            }
            .addOnFailureListener { exception ->
                onComplete(mutableListOf(), exception)
            }
    }

    fun getJobType(onComplete: (MutableList<String>, Exception?) -> Unit) {
        utilites.document("jobType")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val jobType = (document.data?.get("jobType") as MutableList<String>).also {
                        onComplete(it, null)
                    }
                } else {
                    onComplete(mutableListOf(), Exception("No such document"))
                }
            }
            .addOnFailureListener { exception ->
                onComplete(mutableListOf(), exception)
            }
    }
}

class VacancyRepository() {

}