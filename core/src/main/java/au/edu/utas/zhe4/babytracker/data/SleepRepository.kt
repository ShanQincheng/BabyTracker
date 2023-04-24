package au.edu.utas.zhe4.babytracker.data

import au.edu.utas.zhe4.babytracker.domain.Nappy
import au.edu.utas.zhe4.babytracker.domain.Sleep

class SleepRepository(private val dataSource: SleepDataSource) {
    fun addSleep(sleep: Sleep) = dataSource.add(sleep)

    fun readAll(completion: (MutableList<Sleep>) -> Unit) = dataSource.readAll(completion)

    fun modify(sleep: Sleep) = dataSource.modify(sleep)

    fun delete(id: String) = dataSource.delete(id)

}