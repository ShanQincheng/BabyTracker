package au.edu.utas.zhe4.babytracker.usecases

import au.edu.utas.zhe4.babytracker.data.FeedRepository

class DeleteFeed(private val feedRepository: FeedRepository) {
    operator fun invoke(id: String) = feedRepository.delete(id)
}