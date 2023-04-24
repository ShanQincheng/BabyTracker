package au.edu.utas.zhe4.babytracker.framework

import android.util.Log
import au.edu.utas.zhe4.babytracker.domain.FIREBASE_TAG
import au.edu.utas.zhe4.babytracker.data.SleepDataSource
import au.edu.utas.zhe4.babytracker.domain.Sleep
import au.edu.utas.zhe4.babytracker.framework.db.FirebaseDatabase
import com.google.firebase.firestore.ktx.toObject

class FirebaseSleepDataSource : SleepDataSource {
    private var sleepCollection = FirebaseDatabase.getSleepCollection()

    override fun add(sleep: Sleep) {
        // add to the database
        sleepCollection
            .add(sleep)
            .addOnSuccessListener {
                Log.d(FIREBASE_TAG, "Sleep document add with id ${it.id}")
                sleep.id = it.id
            }
            .addOnFailureListener {
                Log.e(FIREBASE_TAG, "Error adding Sleep document", it)
            }
    }

    override fun readAll(completion: (MutableList<Sleep>) -> Unit) {
        val sleepRecords = ArrayList<Sleep>()
        Log.d(FIREBASE_TAG, "before sleep Collection: " + sleepRecords.size)
        //get all feeding records
        sleepCollection
            .get()
            .addOnSuccessListener { result ->

                Log.d(FIREBASE_TAG, "all sleep records size: " + result.size())
                for (document in result)
                {
                    Log.d(FIREBASE_TAG, document.toString())
                    val sleepRecord = document.toObject<Sleep>()
                    sleepRecord.id = document.id
                    Log.d(FIREBASE_TAG, sleepRecord.toString())

                    sleepRecords.add(sleepRecord)
                    Log.d(FIREBASE_TAG, "during feeding Collection: " + sleepRecords.size)
                }
                completion(sleepRecords)
            }
    }

    override fun modify(sleep: Sleep) {
        sleepCollection.document(sleep.id!!)
            .set(sleep)
            .addOnSuccessListener {
                Log.d(
                    FIREBASE_TAG,
                    "Successfully updated sleep record ${sleep.id}")
            }
            .addOnFailureListener {
                Log.e(FIREBASE_TAG, "Error updating sleep record", it)
            }
    }

    override fun delete(id: String) {
        sleepCollection.document(id)
            .delete()
            .addOnSuccessListener {
                Log.d(
                    FIREBASE_TAG,
                    "Successfully delete sleep record $it")
            }
            .addOnFailureListener {
                Log.e(FIREBASE_TAG, "Error delete sleep record", it)
            }
    }
}