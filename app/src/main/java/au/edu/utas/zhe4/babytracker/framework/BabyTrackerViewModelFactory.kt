package au.edu.utas.zhe4.babytracker.framework

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object BabyTrackerViewModelFactory: ViewModelProvider.Factory {
    lateinit var application: Application

    lateinit var dependencies: UseCases

    fun inject(application: Application, dependencies: UseCases) {
        BabyTrackerViewModelFactory.application = application
        BabyTrackerViewModelFactory.dependencies = dependencies
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(BabyTrackerViewModel::class.java.isAssignableFrom(modelClass)) {
            return modelClass.getConstructor(Application::class.java, UseCases::class.java)
                .newInstance(
                    application,
                    dependencies)
        } else {
            throw IllegalStateException("ViewModel must extend BabyTrackerViewModel")
        }
    }

}