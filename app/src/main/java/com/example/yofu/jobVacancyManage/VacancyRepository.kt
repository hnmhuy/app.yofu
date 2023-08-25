package com.example.yofu.jobVacancyManage

import android.util.Log
import com.example.yofu.Vacancy
import com.example.yofu.accountManage.CompanyRepository
import com.example.yofu.accountManage.UserRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val DB_VACANCY = "vacancy"
const val DBV = "vacancy database"

class FormField {
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

class VacancyRepository {
    fun create(newVacancy: Vacancy,
               onComplete: (Vacancy?, Exception?) -> Unit) {
        val db = Firebase.firestore
        val auth = Firebase.auth
        val user = auth.currentUser
        if (user != null) {
            newVacancy.isActive = true
            newVacancy.manager = db.collection("user").document(user.uid.toString())
            // Get company name
            UserRepository().fetch(user.uid.toString()) { user, e ->
                if (e == null)
                {
                    CompanyRepository().fetch(user!!.cid) {company, e ->
                        if (e == null)
                        {
                            newVacancy.companyName = company!!.name
                            Log.d(DBV, "Get company name successfully + ${newVacancy.companyName}")
                            db.collection(DB_VACANCY)
                                .add(newVacancy)
                                .addOnSuccessListener { documentSnapshot ->
                                    newVacancy.vid = documentSnapshot.id
                                    Log.d(DBV, "Create vacancy successfully")
                                    this.update(newVacancy) { e ->
                                        if (e == null) {
                                            Log.d(DBV, "Update vacancy id")
                                        } else {
                                            Log.d(DBV, e.toString())
                                        }
                                    }
                                    onComplete(newVacancy, null)
                                }
                                .addOnFailureListener {
                                    Log.d(DBV, it.toString())
                                    onComplete(null, it)
                                }
                        }
                        else {
                            Log.d(DBV, e.toString())
                            onComplete(null, e)
                            Log.d(DBV, "Get company name failed")
                        }
                    }
                }
            }
        }
        else {
            Log.d(DBV, "No user")
            onComplete(null, Exception("No user"))
        }
    }

    fun update(newVacancy: Vacancy,
               onComplete: (Exception?) -> Unit) {
        // Verify user
        val db = Firebase.firestore
        val auth = Firebase.auth
        val user = auth.currentUser
        if (newVacancy.vid == "" || newVacancy.manager == null) {
            Log.d(DBV, "Make sure that new data has vid and manager")
            onComplete(Exception("Make sure that new data has vid and manager"))
            return
        }
        if (user != null && newVacancy.manager != db.collection("user").document(user.uid)) {
            Log.d(DBV, "This user is not the manager of this vacancy")
            onComplete(Exception("This user is not the manager of this vacancy"))
            return
        }
        db.collection(DB_VACANCY).document(newVacancy.vid)
            .set(newVacancy)
            .addOnSuccessListener {
                Log.d(DBV, "Update vacancy successfully")
                onComplete( null)
            }
            .addOnFailureListener {
                onComplete(it)
            }
    }

    fun fetch(vid: String,
              onComplete: (Vacancy?, Exception?) -> Unit) {
        val db = Firebase.firestore
        db.collection("vacancy").document(vid)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val vacancy = documentSnapshot.toObject(Vacancy::class.java)
                Log.d(DBV, "Fetch vacancy successfully")
                onComplete(vacancy, null)
            }
            .addOnFailureListener {
                Log.d(DBV, it.toString())
                onComplete(null, it)
            }
    }

    fun fetch(vid: DocumentReference, onComplete: (Vacancy?, Exception?) -> Unit) {
        vid.get()
            .addOnSuccessListener { documentSnapshot ->
                val vacancy = documentSnapshot.toObject(Vacancy::class.java)
                Log.d(DBV, "Fetch vacancy successfully")
                onComplete(vacancy, null)
            }
            .addOnFailureListener {
                Log.d(DBV, it.toString())
                onComplete(null, it)
            }
    }

    fun getVacancyList(onComplete: (List<Vacancy>, Exception?) -> Unit)
    {
        val auth = Firebase.auth
        val user = auth.currentUser
        if(user == null)
        {
            Log.d(DBV, "No user.")
            onComplete(mutableListOf(), Exception("No user."))
            return
        }
        val db = Firebase.firestore
        db.collection(DB_VACANCY)
            .whereEqualTo("manager", db.collection("user").document(user.uid))
            .get()
            .addOnSuccessListener { snapShots ->
                Log.d(DBV, "Get vacancy list successfully")
                val vacancyList = mutableListOf<Vacancy>()
                snapShots.forEach {doc ->
                    Log.d(DBV, snapShots.toString())
                    val vacancy = doc.toObject(Vacancy::class.java)
                    vacancyList.add(vacancy)
                }
                Log.d(DBV, "Convert vacancy list successfully")
                // Sort this list by updatedDate
                vacancyList.sortByDescending { it.updatedDate }
                onComplete(vacancyList, null)
            }
            .addOnFailureListener { exception ->
                Log.d(DBV, exception.toString())
                onComplete(mutableListOf(), exception)
            }
    }

    fun getAllVacancies(
        onComplete: (List<Vacancy>) -> Unit,
    ) {
        val db = Firebase.firestore
        Log.d("vacancies", "called")
        db
            .collection(DB_VACANCY)
            .get()
            .addOnSuccessListener { snapShots ->
                val vacancies = mutableListOf<Vacancy>()

                for (document in snapShots.documents) {
//                    vacancies.add(Vacancy.fromFireStore(document))
                    val newVacancy = document.toObject(Vacancy::class.java)
                    if (newVacancy != null && newVacancy.isActive) vacancies.add(newVacancy)
                }
                Log.d("vacancies", "Get list of vacancies successfully")

                onComplete(vacancies)

            }
            .addOnFailureListener() { e ->
                Log.w("vacancies", e.toString())
            }
    }
}