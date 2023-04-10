package au.edu.utas.zhe4.babytracker.usecases

import au.edu.utas.zhe4.babytracker.data.FeedRepository

class ReadAllFeeds(private val feedRepository: FeedRepository) {
    operator fun invoke() = feedRepository.readAll()
}