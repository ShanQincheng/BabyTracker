package au.edu.utas.zhe4.babytracker.domain

import au.edu.utas.zhe4.babytracker.utils.CurrentTime
import au.edu.utas.zhe4.babytracker.utils.DurationStringToLong
import au.edu.utas.zhe4.babytracker.utils.LocalDateTimeToLong
import au.edu.utas.zhe4.babytracker.utils.LongToLocalDateTimeString
import au.edu.utas.zhe4.babytracker.utils.TimeStringToLong
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
    type : String? = FeedingType.BREASTFEEDING.toString(),
    time : String? = LongToLocalDateTimeString(CurrentTime()),
    side : String? = FeedingSide.LEFT.toString(),
    duration : String? = "0",
    note : String? = "",
) : Feed
{
    val feed = Feed()

    feed.id = id
    feed.type = if (type==FeedingType.BREASTFEEDING.toString()) FeedingType.BREASTFEEDING
                else FeedingType.BOTTLE
    feed.time = TimeStringToLong(time!!)
    feed.side = if (side==FeedingSide.LEFT.toString()) FeedingSide.LEFT
                else FeedingSide.RIGHT
    feed.duration = DurationStringToLong(duration!!)
    feed.note = note

    return feed
}
