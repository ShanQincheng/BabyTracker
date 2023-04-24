package au.edu.utas.zhe4.babytracker.data

import au.edu.utas.zhe4.babytracker.domain.Nappy

interface NappyDataSource {
    fun add(nappy : Nappy)

    fun readAll(completion: (MutableList<Nappy>) -> Unit)

    fun modify(nappy : Nappy)
}