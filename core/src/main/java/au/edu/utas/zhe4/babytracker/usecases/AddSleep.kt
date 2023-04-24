package au.edu.utas.zhe4.babytracker.usecases

import au.edu.utas.zhe4.babytracker.data.SleepRepository
import au.edu.utas.zhe4.babytracker.domain.Sleep

class AddSleep(private val sleepRepository: SleepRepository) {
    operator fun invoke(sleep: Sleep) = sleepRepository.addSleep(sleep)
}