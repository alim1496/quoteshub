package com.example.quoteshub

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.android.gms.tasks.OnCompleteListener



class FireBase {
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()


    fun getData(collection: String) {
        db
            .collection(collection)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("success", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("failure", "Error getting documents: ", exception)
            }
    }
}