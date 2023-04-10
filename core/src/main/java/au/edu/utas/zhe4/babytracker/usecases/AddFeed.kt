package au.edu.utas.zhe4.babytracker.usecases

import au.edu.utas.zhe4.babytracker.data.FeedRepository
import au.edu.utas.zhe4.babytracker.domain.Feed

class AddFeed(private val feedRepository: FeedRepository) {
    operator fun invoke(feed: Feed) = feedRepository.addFeed(feed)
}