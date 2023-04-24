package au.edu.utas.zhe4.babytracker.framework

import android.app.Application
import au.edu.utas.zhe4.babytracker.data.FeedRepository
import au.edu.utas.zhe4.babytracker.data.NappyRepository
import au.edu.utas.zhe4.babytracker.usecases.AddFeed
import au.edu.utas.zhe4.babytracker.usecases.AddNappy
import au.edu.utas.zhe4.babytracker.usecases.ModifyFeed
import au.edu.utas.zhe4.babytracker.usecases.ModifyNappy
import au.edu.utas.zhe4.babytracker.usecases.ReadAllFeeds
import au.edu.utas.zhe4.babytracker.usecases.ReadAllNappies

class BabyTrackerApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val feedRepository = FeedRepository(FirebaseFeedDataSource())
        val nappyRepository = NappyRepository(FirebaseNappyDataSource())

        BabyTrackerViewModelFactory.inject(
            this,
            UseCases(
                AddFeed(feedRepository),
                ModifyFeed(feedRepository),
                ReadAllFeeds(feedRepository),

                AddNappy(nappyRepository),
                ModifyNappy(nappyRepository),
                ReadAllNappies(nappyRepository),
            )
        )
    }
}