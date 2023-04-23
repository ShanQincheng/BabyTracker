package au.edu.utas.zhe4.babytracker.presentation.record.feed

import android.app.Application
import androidx.lifecycle.MutableLiveData
import au.edu.utas.zhe4.babytracker.domain.Feed
import au.edu.utas.zhe4.babytracker.domain.FeedingSide
import au.edu.utas.zhe4.babytracker.domain.FeedingType
import au.edu.utas.zhe4.babytracker.domain.createFeed
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModel
import au.edu.utas.zhe4.babytracker.framework.UseCases
import au.edu.utas.zhe4.babytracker.utils.CurrentHour
import au.edu.utas.zhe4.babytracker.utils.CurrentMinute
import au.edu.utas.zhe4.babytracker.utils.CurrentTime
import au.edu.utas.zhe4.babytracker.utils.LocalDateTimeToLocalDateTimeString
import au.edu.utas.zhe4.babytracker.utils.LongToLocalDateTimeString
import java.time.LocalDate


class FeedRecordViewModel(
    application: Application,
    useCases: UseCases,
) : BabyTrackerViewModel(
    application,
    useCases,
) {
    private var ID : String? = ""

    val feedingType = MutableLiveData<String>(FeedingType.BREASTFEEDING.toString())
    val feedingTime = MutableLiveData<String>(LongToLocalDateTimeString(CurrentTime()))
    val feedingSide = MutableLiveData<String>(FeedingSide.LEFT.toString())
    val feedingDuration = MutableLiveData<String>("0")
    var feedingNote = MutableLiveData<String>("0")

    fun setDate(year: Int, month: Int, day: Int) {
        val date = LocalDate.of(year, month, day).atTime(CurrentHour(), CurrentMinute())

        feedingTime.value = LocalDateTimeToLocalDateTimeString(date)
    }

    fun updateID(id: Int) {
        if (id != -1) {
            ID = id.toString()
            return
        }
    }

    fun updateFeedingType(fType: String) {
        feedingType.value = fType
    }

    fun updateFeedingTime(fTime: String) {
        feedingTime.value = fTime
    }

    fun updateFeedingDuration(fDuration: String) {
        feedingDuration.value = fDuration
    }

    fun updateFeedingNote(fNote: String) {
        feedingNote.value = fNote
    }

    fun updateFeedingSide(fSide: String) {
        feedingSide.value = fSide
    }

    fun saveOrUpdateToDatabase() {
        val feedObj : Feed = createFeed(ID,
            feedingType.value,
            feedingTime.value,
            feedingSide.value,
            feedingDuration.value,
            feedingNote.value)

        when(feedObj.id.isNullOrEmpty()) {
            true -> useCases.addFeed(feedObj)
            else -> useCases.modifyFeed(feedObj)
        }
    }
}