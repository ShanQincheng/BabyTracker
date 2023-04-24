package au.edu.utas.zhe4.babytracker.presentation.starttrack.feed

import android.app.Application
import androidx.lifecycle.MutableLiveData
import au.edu.utas.zhe4.babytracker.domain.Feed
import au.edu.utas.zhe4.babytracker.domain.Nappy
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModel
import au.edu.utas.zhe4.babytracker.framework.UseCases

class FeedStartTrackViewModel(
    application: Application,
    useCases: UseCases,
) : BabyTrackerViewModel(
    application,
    useCases,
) {
    companion object {
        const val FIREBASE_TAG = "FirebaseLogging-FeedStartTrack"
        const val FEEDING_RECORD_INDEX = "Feeding_Record_Index"
    }

    val feedingRecords: MutableLiveData<MutableList<Feed>> = MutableLiveData<MutableList<Feed>>()

    init {
        readAllFeedRecords()
    }


    fun readAllFeedRecords() {
        val completion = fun (fRecords: MutableList<Feed>) {
            fRecords.sortWith(Comparator {
                    lRecord, rRecord ->
                if (lRecord.time!! < rRecord.time!!)
                    -1
                else if(lRecord.id!! < rRecord.id!!)
                    1
                else
                    0
            })

            feedingRecords.value?.clear()
            feedingRecords.value = fRecords
        }

        useCases.readAllFeeds(completion)
    }

    fun deleteFeedRecord(id : String) {
        useCases.deleteSleep(id)

        val fRecord = mutableListOf<Feed>()
        for (record in feedingRecords.value!!) {
            if (record.id == id) {
                continue
            }
            fRecord.add(record)
        }

        feedingRecords.value = fRecord
    }
}