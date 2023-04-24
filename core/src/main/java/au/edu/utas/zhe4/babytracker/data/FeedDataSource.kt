package au.edu.utas.zhe4.babytracker.data

import au.edu.utas.zhe4.babytracker.domain.Feed

interface FeedDataSource {
    fun add(feed : Feed)

    fun readAll(completion: (MutableList<Feed>) -> Unit)

    fun modify(feed : Feed)

    fun delete(id : String)
}