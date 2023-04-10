package au.edu.utas.zhe4.babytracker.framework

import android.app.Application
import au.edu.utas.zhe4.babytracker.data.FeedRepository
import au.edu.utas.zhe4.babytracker.usecases.AddFeed
import au.edu.utas.zhe4.babytracker.usecases.ModifyFeed
import au.edu.utas.zhe4.babytracker.usecases.ReadAllFeeds

class BabyTrackerApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val feedRepository = FeedRepository(FirebaseFeedDataSource())

        BabyTrackerViewModelFactory.inject(
            this,
            UseCases(
                AddFeed(feedRepository),
                ReadAllFeeds(feedRepository),
                ModifyFeed(feedRepository)
            )
        )
    }
}