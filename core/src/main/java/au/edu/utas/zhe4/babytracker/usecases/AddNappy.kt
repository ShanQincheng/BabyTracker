package au.edu.utas.zhe4.babytracker.usecases

import au.edu.utas.zhe4.babytracker.data.NappyRepository
import au.edu.utas.zhe4.babytracker.domain.Nappy

class AddNappy(private val nappyRepository: NappyRepository) {
    operator fun invoke(nappy: Nappy) = nappyRepository.addFeed(nappy)
}