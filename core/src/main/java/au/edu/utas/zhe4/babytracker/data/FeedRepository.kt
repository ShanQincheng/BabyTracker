package au.edu.utas.zhe4.babytracker.data

import au.edu.utas.zhe4.babytracker.domain.Feed

class FeedRepository(private val dataSource: FeedDataSource) {
    fun addFeed(feed: Feed) = dataSource.add(feed)

    fun readAll() = dataSource.readAll()

    fun modify(feed: Feed) = dataSource.modify(feed)
}