package au.edu.utas.zhe4.babytracker.presentation.record.nappy

import android.app.Application
import androidx.lifecycle.MutableLiveData
import au.edu.utas.zhe4.babytracker.domain.Feed
import au.edu.utas.zhe4.babytracker.domain.FeedingSide
import au.edu.utas.zhe4.babytracker.domain.FeedingType
import au.edu.utas.zhe4.babytracker.domain.Nappy
import au.edu.utas.zhe4.babytracker.domain.NappyCons
import au.edu.utas.zhe4.babytracker.domain.createFeed
import au.edu.utas.zhe4.babytracker.domain.createNappy
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModel
import au.edu.utas.zhe4.babytracker.framework.UseCases
import au.edu.utas.zhe4.babytracker.utils.CurrentTime
import au.edu.utas.zhe4.babytracker.utils.LocalDateTimeToLocalDateTimeString
import au.edu.utas.zhe4.babytracker.utils.LongToLocalDateTimeString
import au.edu.utas.zhe4.babytracker.utils.TimeStringToLocalDateTime
import au.edu.utas.zhe4.babytracker.utils.randomUUID
import java.time.LocalDate

class NappyRecordViewModel(
    application: Application,
    useCases: UseCases,
) : BabyTrackerViewModel(
    application,
    useCases,
) {
    private var ID : String? = ""

    val nappyCons = MutableLiveData<String>(NappyCons.WET.toString())
    val nappyTime = MutableLiveData<String>(LongToLocalDateTimeString(CurrentTime()))
    var nappyNote = MutableLiveData<String>("")

    override fun setDate(year: Int, month: Int, day: Int) {
        val hour = TimeStringToLocalDateTime(nappyTime.value).hour
        val minute = TimeStringToLocalDateTime(nappyTime.value).minute

        val datetime = LocalDate.of(year, month, day).atTime(hour, minute)

        nappyTime.value = LocalDateTimeToLocalDateTimeString(datetime)
    }

    override fun setTime(hour: Int, minute: Int) {
        val year = TimeStringToLocalDateTime(nappyTime.value).year
        val month = TimeStringToLocalDateTime(nappyTime.value).month
        val day = TimeStringToLocalDateTime(nappyTime.value).dayOfMonth

        val date = LocalDate.of(year, month, day).atTime(hour, minute)

        nappyTime.value = LocalDateTimeToLocalDateTimeString(date)
    }

    fun updateID(id: String) {
        ID = id
    }

    fun updateNappyCons(npyCons: String) {
        nappyCons.value = npyCons
    }

    fun updateNappyTime(npyTime: String) {
        nappyTime.value = npyTime
    }

    fun updateNappyNote(npyNote: String) {
        nappyNote.value = npyNote
    }

    fun saveOrUpdateToDatabase() {
        val npyObj : Nappy = createNappy(ID,
            nappyTime.value,
            nappyCons.value,
            "",
            nappyNote.value)

        when(npyObj.id.isNullOrEmpty()) {
            true -> {
                npyObj.id = randomUUID()
                useCases.addNappy(npyObj)
            }
            else ->useCases.modifyNappy(npyObj)
        }
    }
}