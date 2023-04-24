package au.edu.utas.zhe4.babytracker.usecases

import au.edu.utas.zhe4.babytracker.data.SleepRepository
import au.edu.utas.zhe4.babytracker.domain.Sleep

class DeleteSleep(private val sleepRepository: SleepRepository) {
    operator fun invoke(id: String) = sleepRepository.delete(id)
}