package au.edu.utas.zhe4.babytracker.presentation.starttrack.feed

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import au.edu.utas.zhe4.babytracker.domain.Feed
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModel
import au.edu.utas.zhe4.babytracker.framework.UseCases
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

    init {
        readAllFeedRecords()
    }

    val feedingRecords: MutableLiveData<MutableList<Feed>> = MutableLiveData<MutableList<Feed>>()

    fun readAllFeedRecords() {
        val completion = fun (fRecords: MutableList<Feed>) {
            feedingRecords.value?.clear()
            feedingRecords.postValue(fRecords)
        }

        useCases.readAllFeeds(completion)
//        useCases.readAllFeeds{
//            fRecords -> run{
//                feedingRecords.value?.clear()
//                feedingRecords.value = fRecords
//            }
//        }
    }
}