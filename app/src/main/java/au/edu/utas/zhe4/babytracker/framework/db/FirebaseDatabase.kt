package au.edu.utas.zhe4.babytracker.framework.db

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

abstract class FirebaseDatabase {
    companion object {
        fun getInstance() : FirebaseFirestore = Firebase.firestore

        fun getFeedingCollection() : CollectionReference =
            getInstance().collection("feedings")

        fun getNappyCollection() : CollectionReference =
            getInstance().collection("nappies")
    }
}