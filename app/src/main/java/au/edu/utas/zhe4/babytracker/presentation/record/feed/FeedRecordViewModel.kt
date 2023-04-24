package au.edu.utas.zhe4.babytracker.presentation.record.feed

import android.app.Application
import androidx.lifecycle.MutableLiveData
import au.edu.utas.zhe4.babytracker.domain.Feed
import au.edu.utas.zhe4.babytracker.domain.FeedingSide
import au.edu.utas.zhe4.babytracker.domain.FeedingType
import au.edu.utas.zhe4.babytracker.domain.createFeed
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModel
import au.edu.utas.zhe4.babytracker.framework.UseCases
import au.edu.utas.zhe4.babytracker.utils.CurrentTime
import au.edu.utas.zhe4.babytracker.utils.LocalDateTimeToLocalDateTimeString
import au.edu.utas.zhe4.babytracker.utils.LongToLocalDateTimeString
import au.edu.utas.zhe4.babytracker.utils.TimeStringToLocalDateTime
import au.edu.utas.zhe4.babytracker.utils.randomUUID
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
    var feedingNote = MutableLiveData<String>("")

    override fun setDate(year: Int, month: Int, day: Int) {
        val hour = TimeStringToLocalDateTime(feedingTime.value).hour
        val minute = TimeStringToLocalDateTime(feedingTime.value).minute

        val datetime = LocalDate.of(year, month, day).atTime(hour, minute)

        feedingTime.value = LocalDateTimeToLocalDateTimeString(datetime)
    }

    override fun setTime(hour: Int, minute: Int) {
        val year = TimeStringToLocalDateTime(feedingTime.value).year
        val month = TimeStringToLocalDateTime(feedingTime.value).month
        val day = TimeStringToLocalDateTime(feedingTime.value).dayOfMonth

        val date = LocalDate.of(year, month, day).atTime(hour, minute)

        feedingTime.value = LocalDateTimeToLocalDateTimeString(date)
    }

    fun updateID(id: String) {
        ID = id
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
            true -> {
                feedObj.id = randomUUID()
                useCases.addFeed(feedObj)
            }
            else -> useCases.modifyFeed(feedObj)
        }
    }
}