package au.edu.utas.zhe4.babytracker.usecases

import au.edu.utas.zhe4.babytracker.data.SleepRepository

class DeleteSleep(private val sleepRepository: SleepRepository) {
    operator fun invoke(id: String) = sleepRepository.delete(id)
}