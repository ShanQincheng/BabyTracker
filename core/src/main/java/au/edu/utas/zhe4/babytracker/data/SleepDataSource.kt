package au.edu.utas.zhe4.babytracker.data

import au.edu.utas.zhe4.babytracker.domain.Feed
import au.edu.utas.zhe4.babytracker.domain.Sleep

interface SleepDataSource {
    fun add(sleep : Sleep)

    fun readAll(completion: (MutableList<Sleep>) -> Unit)

    fun modify(sleep : Sleep)
}