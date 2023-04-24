package au.edu.utas.zhe4.babytracker.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.TimeZone
import java.util.UUID

val dateTimeformatPattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

fun randomUUID(): String {
    return UUID.randomUUID().toString()
}
fun CurrentYear(): Int {
    val rightNow = Calendar.getInstance()
    return rightNow.get(Calendar.YEAR)
}

fun CurrentMonth(): Int {
    val rightNow = Calendar.getInstance()
    // Keep in mind that months values start from 0, so October is actually month number 9.
    // https://stackoverflow.com/a/19348087/12997752
    return rightNow.get(Calendar.MONTH) + 1
}

fun CurrentDay(): Int {
    val rightNow = Calendar.getInstance()
    return rightNow.get(Calendar.DAY_OF_MONTH)
}

fun CurrentHour(): Int {
    val rightNow = Calendar.getInstance()
    return rightNow.get(Calendar.HOUR_OF_DAY)
}

fun CurrentMinute(): Int {
    val rightNow = Calendar.getInstance()
    return rightNow.get(Calendar.MINUTE)
}

// to string format: "dd/MM/yyyy HH:mm"
fun LongToLocalDateTimeString(timeLong: Long) : String {
    val formatPattern = "dd/MM/yyyy HH:mm"

    return LocalDateTime.ofInstant(
        Instant.ofEpochMilli(
            timeLong
        ),
        TimeZone.getDefault().toZoneId(),
    ).format(DateTimeFormatter.ofPattern(formatPattern)).toString()
}

fun LocalDateTimeToLocalDateTimeString(datetime: LocalDateTime): String {
    return datetime.format(dateTimeformatPattern)
}

fun TimeStringToLocalDateTime(timeString: String? = "10/04/2023 18:48") : LocalDateTime {
    val formatPattern = "dd/MM/yyyy HH:mm"
    val formatter = DateTimeFormatter.ofPattern(formatPattern)

    return LocalDateTime.parse(timeString, formatter)
}

fun LocalDateTimeToLong(time: LocalDateTime? = LocalDateTime.now()): Long {
    return ZonedDateTime.of(time, ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun TimeStringToLong(timeString: String) : Long {
    val formatPattern = "dd/MM/yyyy HH:mm"
    val formatter = DateTimeFormatter.ofPattern(formatPattern)
    var time : LocalDateTime = LocalDateTime.parse(timeString, formatter)

    return ZonedDateTime.of(time, ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun DurationStringToLong(durationString: String): Long {
    return durationString.toLong()
}

fun CurrentTime() : Long {
    return System.currentTimeMillis()
}