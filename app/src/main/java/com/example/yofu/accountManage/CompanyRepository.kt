package com.example.yofu.accountManage

import android.util.Log
import com.example.yofu.Company
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

const val DB_COMPANY = "company"
const val DBC = "company database"

class CompanyRepository {

    fun fetch(cid: String, onComplete: (DocumentReference?, Company?, Exception?) -> Unit) {
        if(cid == "") {
            Log.d(DBC, "Fetch failed: CID is empty")
            onComplete(null, null, Exception("CID is empty"))
            return
        }
        val db = Firebase.firestore
        val docRef = db.collection("company").document(cid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val res = document.toObject<Company>()
                    Log.d(DBC, "Fetch successful, id = ${document.data}")
                    onComplete(docRef, res, null)
                } else {
                    Log.d(DBC, "No such document")
                    onComplete(null, null, Exception("No such document"))
                }
            }
            .addOnFailureListener { exception ->
                Log.d(DBC, "Fetch failed: ", exception)
                onComplete(null, null, exception)
            }
    }

    fun fetch(ref: DocumentReference, onComplete: (Company?, Exception?) -> Unit) {
        ref.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val res = document.toObject<Company>()
                    Log.d(DBC, "Fetch successful, id = ${document.data}")
                    onComplete(res, null)
                } else {
                    Log.d(DBC, "No such document")
                    onComplete(null, Exception("No such document"))
                }
            }
            .addOnFailureListener { exception ->
                Log.d(DBC, "Fetch failed: ", exception)
                onComplete(null, exception)
            }
    }

    fun update(newData: Company, onComplete: (Exception?) -> Unit) {
        if (newData.cid == "") {
            onComplete(Exception("UID is empty"))
            return
        }
        val db = Firebase.firestore
        db.collection("company").document(newData.cid)
            .set(newData)
            .addOnSuccessListener {
                Log.d(DBC, "DocumentSnapshot successfully written!")
                onComplete(null)
            }
            .addOnFailureListener { exception ->
                Log.w(DBC, "Error writing document", exception)
                onComplete(exception)
            }
    }

    fun create(newData: Company, onComplete: (Company, Exception?) -> Unit) {
        val db = Firebase.firestore
        val docRef = db.collection("company")
            .add(newData)
            .addOnSuccessListener { documentReference ->
                Log.d(DBC, "DocumentSnapshot successfully written!")
                newData.cid = documentReference.id
                onComplete(newData, null)
            }
            .addOnFailureListener { exception ->
                Log.w(DBC, "Error writing document", exception)
                onComplete(newData, exception)
            }
    }
}