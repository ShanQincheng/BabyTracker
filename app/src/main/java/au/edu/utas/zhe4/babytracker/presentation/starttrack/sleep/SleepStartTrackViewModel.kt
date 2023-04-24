package au.edu.utas.zhe4.babytracker.presentation.starttrack.sleep

import android.app.Application
import androidx.lifecycle.MutableLiveData
import au.edu.utas.zhe4.babytracker.domain.Sleep
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModel
import au.edu.utas.zhe4.babytracker.framework.UseCases

class SleepStartTrackViewModel(
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

    val sleepRecords: MutableLiveData<MutableList<Sleep>> = MutableLiveData<MutableList<Sleep>>()

    init {
        readAllSleepRecords()
    }

    fun readAllSleepRecords() {
        val completion = fun (slpRecords: MutableList<Sleep>) {
            slpRecords.sortWith(Comparator {
                    lRecord, rRecord ->
                if (lRecord.time!! < rRecord.time!!)
                    -1
                else if(lRecord.id!! < rRecord.id!!)
                    1
                else
                    0
            })

            sleepRecords.value?.clear()
            sleepRecords.value = slpRecords
        }

        useCases.readAllSleeps(completion)
    }
}