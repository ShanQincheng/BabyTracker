package au.edu.utas.zhe4.babytracker.data

import au.edu.utas.zhe4.babytracker.domain.Feed

class FeedRepository(private val dataSource: FeedDataSource) {
    fun addFeed(feed: Feed) = dataSource.add(feed)

    fun readAll(completion: (MutableList<Feed>) -> Unit) = dataSource.readAll(completion)

    fun modify(feed: Feed) = dataSource.modify(feed)

    fun delete(id: String) = dataSource.delete(id)
}