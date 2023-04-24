package au.edu.utas.zhe4.babytracker.presentation.record.sleep

import android.app.Application
import androidx.lifecycle.MutableLiveData
import au.edu.utas.zhe4.babytracker.domain.Sleep
import au.edu.utas.zhe4.babytracker.domain.createSleep
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModel
import au.edu.utas.zhe4.babytracker.framework.UseCases
import au.edu.utas.zhe4.babytracker.utils.CurrentTime
import au.edu.utas.zhe4.babytracker.utils.LocalDateTimeToLocalDateTimeString
import au.edu.utas.zhe4.babytracker.utils.LongToLocalDateTimeString
import au.edu.utas.zhe4.babytracker.utils.TimeStringToLocalDateTime
import au.edu.utas.zhe4.babytracker.utils.randomUUID
import java.time.LocalDate

class SleepRecordViewModel(
    application: Application,
    useCases: UseCases,
) : BabyTrackerViewModel(
    application,
    useCases,
) {
    private var ID : String? = ""

    val sleepTime = MutableLiveData<String>(LongToLocalDateTimeString(CurrentTime()))
    val sleepStartTime = MutableLiveData<String>("8:00")
    val sleepEndTime = MutableLiveData<String>("12:00")
    var sleepNote = MutableLiveData<String>("")

    override fun setDate(year: Int, month: Int, day: Int) {
        val hour = TimeStringToLocalDateTime(sleepTime.value).hour
        val minute = TimeStringToLocalDateTime(sleepTime.value).minute

        val datetime = LocalDate.of(year, month, day).atTime(hour, minute)

        sleepTime.value = LocalDateTimeToLocalDateTimeString(datetime)
    }

    override fun setTime(hour: Int, minute: Int) {
        val year = TimeStringToLocalDateTime(sleepTime.value).year
        val month = TimeStringToLocalDateTime(sleepTime.value).month
        val day = TimeStringToLocalDateTime(sleepTime.value).dayOfMonth

        val date = LocalDate.of(year, month, day).atTime(hour, minute)

        sleepTime.value = LocalDateTimeToLocalDateTimeString(date)
    }

    fun setStartTime(hour: Int, minute: Int) {
        sleepStartTime.value = "$hour:$minute"
    }

    fun setEndTime(hour: Int, minute: Int) {
        sleepEndTime.value = "$hour:$minute"
    }

    fun updateID(id: String) {
        ID = id
    }

    fun updateSleepTime(slpTime: String) {
        sleepTime.value = slpTime
    }

    fun updateSleepStartTime(slpStartTime: String) {
        sleepStartTime.value = slpStartTime
    }

    fun updateSleepEndTime(slpEndTime: String) {
        sleepEndTime.value = slpEndTime
    }

    fun updateSleepNote(slpNote: String) {
        sleepNote.value = slpNote
    }

    fun saveOrUpdateToDatabase() {
        val slpObj : Sleep = createSleep(ID,
            sleepTime.value,
            sleepStartTime.value,
            sleepEndTime.value,
            sleepNote.value)

        when(slpObj.id.isNullOrEmpty()) {
            true -> {
                slpObj.id = randomUUID()
                useCases.addSleep(slpObj)
            }
            else ->useCases.modifySleep(slpObj)
        }
    }
}