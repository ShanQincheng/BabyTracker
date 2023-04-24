package au.edu.utas.zhe4.babytracker.data

import au.edu.utas.zhe4.babytracker.domain.Nappy

class NappyRepository(private val dataSource: NappyDataSource) {
    fun addFeed(nappy: Nappy) = dataSource.add(nappy)

    fun readAll(completion: (MutableList<Nappy>) -> Unit) = dataSource.readAll(completion)

    fun modify(nappy: Nappy) = dataSource.modify(nappy)

}