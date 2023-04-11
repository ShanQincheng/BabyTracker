package au.edu.utas.zhe4.babytracker.usecases

import au.edu.utas.zhe4.babytracker.data.FeedRepository
import au.edu.utas.zhe4.babytracker.domain.Feed

class ReadAllFeeds(private val feedRepository: FeedRepository) {
    operator fun invoke(completion: (MutableList<Feed>) -> Unit) =
        feedRepository.readAll(completion)
}