package au.edu.utas.zhe4.babytracker.framework

import au.edu.utas.zhe4.babytracker.usecases.AddFeed
import au.edu.utas.zhe4.babytracker.usecases.AddNappy
import au.edu.utas.zhe4.babytracker.usecases.AddSleep
import au.edu.utas.zhe4.babytracker.usecases.ModifyFeed
import au.edu.utas.zhe4.babytracker.usecases.ModifyNappy
import au.edu.utas.zhe4.babytracker.usecases.ModifySleep
import au.edu.utas.zhe4.babytracker.usecases.ReadAllFeeds
import au.edu.utas.zhe4.babytracker.usecases.ReadAllNappies
import au.edu.utas.zhe4.babytracker.usecases.ReadAllSleeps

data class UseCases (
    val addFeed: AddFeed,
    val modifyFeed: ModifyFeed,
    val readAllFeeds: ReadAllFeeds,

    val addNappy: AddNappy,
    val modifyNappy: ModifyNappy,
    val readAllNappies: ReadAllNappies,

    val addSleep: AddSleep,
    val modifySleep: ModifySleep,
    val readAllSleeps: ReadAllSleeps,
)