package au.edu.utas.zhe4.babytracker.domain

import au.edu.utas.zhe4.babytracker.utils.CurrentTime
import au.edu.utas.zhe4.babytracker.utils.LongToLocalDateTimeString
import au.edu.utas.zhe4.babytracker.utils.TimeStringToLong

class Sleep {
    var id : String? = null
    var time : Long? = System.currentTimeMillis()
    var startTime : String? = "8:00"
    var endTime : String? = "12:00"
    var note : String? = ""
}

fun createSleep(
    id : String? = null,
    time : String? = LongToLocalDateTimeString(CurrentTime()),
    startTime : String? = "8:00",
    endTime : String? = "12:00",
    note : String? = "",
) : Sleep
{
    val sleep = Sleep()

    sleep.id = id
    sleep.time = TimeStringToLong(time!!)
    sleep.startTime = startTime
    sleep.endTime = endTime
    sleep.note = note

    return sleep
}