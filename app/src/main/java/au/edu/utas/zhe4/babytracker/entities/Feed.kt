package au.edu.utas.zhe4.babytracker.entities

import com.google.firebase.firestore.Exclude
import java.sql.Timestamp
import java.time.Instant
import kotlin.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

enum class FeedingType {
    BREASTFEEDING, BOTTLE
}

enum class FeedingSide {
    LEFT, RIGHT
}

class Feed() {
    @get:Exclude var id : String? = null

    var type : FeedingType? = FeedingType.BREASTFEEDING
    var time : Long? = System.currentTimeMillis()
    var side : FeedingSide? = FeedingSide.LEFT
    var duration : Long? = 0
    var note : String? = ""
}

fun createFeed(
    type : FeedingType? = FeedingType.BREASTFEEDING,
    time : LocalDateTime? = LocalDateTime.now(),
    side : FeedingSide? = FeedingSide.LEFT,
    duration : Duration? = Duration.ZERO,
    note : String? = "",
) : Feed
{
    val feed = Feed()
    feed.type = type
    feed.time = ZonedDateTime.of(time, ZoneId.systemDefault()).toInstant().toEpochMilli()
    feed.side = side
    feed.duration = duration?.toLong(DurationUnit.SECONDS)
    feed.note = note

    return feed
}



