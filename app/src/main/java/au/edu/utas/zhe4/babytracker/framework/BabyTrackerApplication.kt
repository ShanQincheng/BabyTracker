package au.edu.utas.zhe4.babytracker.framework

import android.app.Application
import au.edu.utas.zhe4.babytracker.data.FeedRepository
import au.edu.utas.zhe4.babytracker.data.NappyRepository
import au.edu.utas.zhe4.babytracker.data.SleepRepository
import au.edu.utas.zhe4.babytracker.usecases.AddFeed
import au.edu.utas.zhe4.babytracker.usecases.AddNappy
import au.edu.utas.zhe4.babytracker.usecases.AddSleep
import au.edu.utas.zhe4.babytracker.usecases.DeleteFeed
import au.edu.utas.zhe4.babytracker.usecases.DeleteNappy
import au.edu.utas.zhe4.babytracker.usecases.DeleteSleep
import au.edu.utas.zhe4.babytracker.usecases.ModifyFeed
import au.edu.utas.zhe4.babytracker.usecases.ModifyNappy
import au.edu.utas.zhe4.babytracker.usecases.ModifySleep
import au.edu.utas.zhe4.babytracker.usecases.ReadAllFeeds
import au.edu.utas.zhe4.babytracker.usecases.ReadAllNappies
import au.edu.utas.zhe4.babytracker.usecases.ReadAllSleeps

class BabyTrackerApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val feedRepository = FeedRepository(FirebaseFeedDataSource())
        val nappyRepository = NappyRepository(FirebaseNappyDataSource())
        val sleepRepository = SleepRepository(FirebaseSleepDataSource())

        BabyTrackerViewModelFactory.inject(
            this,
            UseCases(
                AddFeed(feedRepository),
                ModifyFeed(feedRepository),
                ReadAllFeeds(feedRepository),
                DeleteFeed(feedRepository),

                AddNappy(nappyRepository),
                ModifyNappy(nappyRepository),
                ReadAllNappies(nappyRepository),
                DeleteNappy(nappyRepository),

                AddSleep(sleepRepository),
                ModifySleep(sleepRepository),
                ReadAllSleeps(sleepRepository),
                DeleteSleep(sleepRepository),
            )
        )
    }
}