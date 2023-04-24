package au.edu.utas.zhe4.babytracker.framework

import android.net.Uri
import android.util.Log
import au.edu.utas.zhe4.babytracker.FIREBASE_TAG
import au.edu.utas.zhe4.babytracker.data.NappyDataSource
import au.edu.utas.zhe4.babytracker.domain.Nappy
import au.edu.utas.zhe4.babytracker.framework.db.FirebaseDatabase
import au.edu.utas.zhe4.babytracker.framework.db.FirebaseDatabase.Companion.getStorageReference
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.FileInputStream


class FirebaseNappyDataSource : NappyDataSource {
    private var nappyCollection = FirebaseDatabase.getNappyCollection()

    override fun add(nappy: Nappy) {
        // add to the database
        nappyCollection
            .add(nappy)
            .addOnSuccessListener {
                Log.d(FIREBASE_TAG, "Nappy document add with id ${it.id}")
                nappy.id = it.id
            }
            .addOnFailureListener {
                Log.e(FIREBASE_TAG, "Error adding Nappy document", it)
            }
    }

    override fun readAll(completion: (MutableList<Nappy>) -> Unit) {
        val nappyRecords = ArrayList<Nappy>()
        Log.d(FIREBASE_TAG, "before nappy Collection: " + nappyRecords.size)
        //get all feeding records
        nappyCollection
            .get()
            .addOnSuccessListener { result ->

                Log.d(FIREBASE_TAG, "all nappy records size: " + result.size())
                for (document in result)
                {
                    Log.d(FIREBASE_TAG, document.toString())
                    val npyRecord = document.toObject<Nappy>()
                    npyRecord.id = document.id
                    Log.d(FIREBASE_TAG, npyRecord.toString())

                    nappyRecords.add(npyRecord)
                    Log.d(FIREBASE_TAG, "during feeding Collection: " + nappyRecords.size)
                }
                completion(nappyRecords)
            }
    }

    override fun modify(nappy: Nappy) {
        nappyCollection.document(nappy.id!!)
            .set(nappy)
            .addOnSuccessListener {
                Log.d(
                    FIREBASE_TAG,
                    "Successfully updated nappy record ${nappy.id}")
            }
            .addOnFailureListener {
                Log.e(FIREBASE_TAG, "Error updating nappy record", it)
            }
    }
}