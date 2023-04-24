package au.edu.utas.zhe4.babytracker.usecases

import au.edu.utas.zhe4.babytracker.data.SleepRepository
import au.edu.utas.zhe4.babytracker.domain.Sleep

class ReadAllSleeps(private val sleepRepository: SleepRepository) {
    operator fun invoke(completion: (MutableList<Sleep>) -> Unit) =
        sleepRepository.readAll(completion)
}