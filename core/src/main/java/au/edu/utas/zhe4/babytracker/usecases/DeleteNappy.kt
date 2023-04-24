package au.edu.utas.zhe4.babytracker.usecases

import au.edu.utas.zhe4.babytracker.data.NappyRepository

class DeleteNappy(private val nappyRepository: NappyRepository) {
    operator fun invoke(id: String) = nappyRepository.delete(id)
}