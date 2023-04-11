package au.edu.utas.zhe4.babytracker.domain

import au.edu.utas.zhe4.babytracker.utils.LocalDateTimeToLong
//import com.google.firebase.firestore.Exclude
import java.time.LocalDateTime

enum class FeedingType {
    BREASTFEEDING, BOTTLE
}

enum class FeedingSide {
    LEFT, RIGHT
}

class Feed() {
    var id : String? = null

    var type : FeedingType? = FeedingType.BREASTFEEDING
    var time : Long? = System.currentTimeMillis()
    var side : FeedingSide? = FeedingSide.LEFT
    var duration : Long? = 0
    var note : String? = ""
}

fun createFeed(
    id : String? = null,
    type : FeedingType? = FeedingType.BREASTFEEDING,
    time : LocalDateTime? = LocalDateTime.now(),
    side : FeedingSide? = FeedingSide.LEFT,
    duration : Long? = 0,
    note : String? = "",
) : Feed
{
    val feed = Feed()
    feed.id = id
    feed.type = type
    feed.time = LocalDateTimeToLong(time)
    feed.side = side
    feed.duration = duration
    feed.note = note

    return feed
}