package au.edu.utas.zhe4.babytracker.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel

open class BabyTrackerViewModel(application: Application, protected val useCases: UseCases)  :
    AndroidViewModel(application)
{
    protected val application: BabyTrackerApplication = getApplication()

    open fun setDate(year: Int, month: Int, day: Int){}

    open fun setTime(hour: Int, minute: Int){}
}