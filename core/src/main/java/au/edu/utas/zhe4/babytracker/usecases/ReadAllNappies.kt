package au.edu.utas.zhe4.babytracker.usecases

import au.edu.utas.zhe4.babytracker.data.NappyRepository
import au.edu.utas.zhe4.babytracker.domain.Nappy

class ReadAllNappies(private val nappyRepository: NappyRepository) {
    operator fun invoke(completion: (MutableList<Nappy>) -> Unit) =
        nappyRepository.readAll(completion)
}