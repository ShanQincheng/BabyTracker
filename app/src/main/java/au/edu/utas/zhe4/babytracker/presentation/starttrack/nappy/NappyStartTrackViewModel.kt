package au.edu.utas.zhe4.babytracker.presentation.starttrack.nappy

import android.app.Application
import androidx.lifecycle.MutableLiveData
import au.edu.utas.zhe4.babytracker.domain.Nappy
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModel
import au.edu.utas.zhe4.babytracker.framework.UseCases

class NappyStartTrackViewModel(
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

    val nappyRecords: MutableLiveData<MutableList<Nappy>> = MutableLiveData<MutableList<Nappy>>()

    init {
        readAllNappyRecords()
    }

    fun readAllNappyRecords() {
        val completion = fun (npyRecords: MutableList<Nappy>) {
            npyRecords.sortWith(Comparator {
                    lRecord, rRecord ->
                if (lRecord.time!! < rRecord.time!!)
                    -1
                else if(lRecord.id!! < rRecord.id!!)
                    1
                else
                    0
            })

            nappyRecords.value?.clear()
            nappyRecords.value = npyRecords
        }

        useCases.readAllNappies(completion)
    }
}