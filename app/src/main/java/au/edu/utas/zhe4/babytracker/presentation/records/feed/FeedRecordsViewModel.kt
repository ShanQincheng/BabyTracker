package au.edu.utas.zhe4.babytracker.presentation.records.feed

import android.app.Application
import androidx.lifecycle.MutableLiveData
import au.edu.utas.zhe4.babytracker.domain.Feed
import au.edu.utas.zhe4.babytracker.domain.FeedingSide
import au.edu.utas.zhe4.babytracker.domain.FeedingType
import au.edu.utas.zhe4.babytracker.framework.BabyTrackerViewModel
import au.edu.utas.zhe4.babytracker.framework.UseCases

class FeedRecordsViewModel(
    application: Application,
    useCases: UseCases,
) : BabyTrackerViewModel(
    application,
    useCases,
) {
    //var id : String? = null
    var type: MutableLiveData<FeedingType> = MutableLiveData<FeedingType>(FeedingType.BREASTFEEDING)
    //var type : FeedingType? = FeedingType.BREASTFEEDING
//    var time : Long? = System.currentTimeMillis()
//    var side : FeedingSide? = FeedingSide.LEFT
//    var duration : Long? = 0
//    var note : String? = ""
}