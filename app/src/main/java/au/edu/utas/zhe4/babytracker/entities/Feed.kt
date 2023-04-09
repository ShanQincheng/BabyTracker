package au.edu.utas.zhe4.babytracker.entities

import com.google.firebase.firestore.Exclude
import kotlin.time.Duration
import java.time.LocalDateTime

enum class FeedingType {
    BREASTFEEDING, BOTTLE
}

enum class FeedingSide {
    LEFT, RIGHT
}

class Feed
(
   var type : FeedingType,
   var time : LocalDateTime,
   var side : FeedingSide,
   var duration : Duration,
   var note : String,
) {
    @get:Exclude var id : String? = null
    init {
        type = type
        time = time
        side = side
        duration = duration
        note = note
    }
    //var type : FeedingType? = FeedingType.BREASTFEEDING
    //var time : LocalDateTime? = LocalDateTime.now()
    //var side : FeedingSide? = FeedingSide.LEFT
    //var duration : Duration? = Duration.ZERO
    //var note : String? = ""
}



