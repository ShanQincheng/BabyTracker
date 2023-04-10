package au.edu.utas.zhe4.babytracker.framework

import au.edu.utas.zhe4.babytracker.usecases.AddFeed
import au.edu.utas.zhe4.babytracker.usecases.ModifyFeed
import au.edu.utas.zhe4.babytracker.usecases.ReadAllFeeds

data class UseCases (
    val addFeed: AddFeed,
    val readAllFeeds: ReadAllFeeds,
    val modifyFeed: ModifyFeed
)