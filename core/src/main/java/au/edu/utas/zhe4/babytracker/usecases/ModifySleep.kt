package au.edu.utas.zhe4.babytracker.usecases

import au.edu.utas.zhe4.babytracker.data.SleepRepository
import au.edu.utas.zhe4.babytracker.domain.Sleep

class ModifySleep(private val sleepRepository: SleepRepository) {
    operator fun invoke(sleep: Sleep) = sleepRepository.modify(sleep)
}