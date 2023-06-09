package au.edu.utas.zhe4.babytracker.framework

import android.util.Log
import au.edu.utas.zhe4.babytracker.data.FeedDataSource
import au.edu.utas.zhe4.babytracker.domain.FIREBASE_TAG
import au.edu.utas.zhe4.babytracker.domain.Feed
import au.edu.utas.zhe4.babytracker.framework.db.FirebaseDatabase
import com.google.firebase.firestore.ktx.toObject

class FirebaseFeedDataSource : FeedDataSource {
    private var feedingCollection = FirebaseDatabase.getFeedingCollection()

    override fun add(feed: Feed) {
        // add to the database
        feedingCollection
            .add(feed)
            .addOnSuccessListener {
                Log.d(FIREBASE_TAG, "Feed document add with id ${it.id}")
                feed.id = it.id
            }
            .addOnFailureListener {
                Log.e(FIREBASE_TAG, "Error adding Feed document", it)
            }
    }

    override fun readAll(completion: (MutableList<Feed>) -> Unit) {
        val feedingRecords = ArrayList<Feed>()
        Log.d(FIREBASE_TAG, "before feeding Collection: " + feedingRecords.size)
        //get all feeding records
        feedingCollection
            .get()
            .addOnSuccessListener { result ->

                Log.d(FIREBASE_TAG, "all feeding records size: " + result.size())
                for (document in result)
                {
                    Log.d(FIREBASE_TAG, document.toString())
                    val fRecord = document.toObject<Feed>()
                    fRecord.id = document.id
                    Log.d(FIREBASE_TAG, fRecord.toString())

                    feedingRecords.add(fRecord)
                    Log.d(FIREBASE_TAG, "during feeding Collection: " + feedingRecords.size)
                }
                completion(feedingRecords)
            }
    }

    override fun modify(feed: Feed) {
        feedingCollection.document(feed.id!!)
            .set(feed)
            .addOnSuccessListener {
                Log.d(FIREBASE_TAG,
                    "Successfully updated feeding record ${feed.id}")
            }
            .addOnFailureListener {
                Log.e(FIREBASE_TAG, "Error updating feeding record", it)
            }
    }

    override fun delete(id: String) {
        feedingCollection.document(id)
            .delete()
            .addOnSuccessListener {
                Log.d(
                    FIREBASE_TAG,
                    "Successfully delete feeding record $it")
            }
            .addOnFailureListener {
                Log.e(FIREBASE_TAG, "Error delete feeding record", it)
            }
    }
}