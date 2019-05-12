package com.github.jeremyrempel.unsplash.presentation

import com.github.jeremyrempel.unsplash.LogLevel
import com.github.jeremyrempel.unsplash.api.PhotoApi
import com.github.jeremyrempel.unsplash.db.Database
import com.github.jeremyrempel.unsplash.di.kodein
import com.github.jeremyrempel.unsplash.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.erased.instance
import kotlin.coroutines.CoroutineContext

class PhotoPresenter(
    uiContext: CoroutineContext,
    private val view: PhotoView
) : CoroutinePresenter(uiContext, view), PhotoActions {

    private val api: PhotoApi by kodein.instance()

    override fun onRequestData() = updateData()

    private fun updateData() {
        view.isUpdating = true

        GlobalScope.launch(coroutineContext) {
            val response = api.getRandom()
            view.onUpdate(response)

            // SQLDelight usage sample from multi platform common code
            val db = Database.instance
            db.playerQueries.selectAll().executeAsList().forEach {
                log(LogLevel.DEBUG, "DB Common", it.toString())
            }
        }.invokeOnCompletion {
            view.isUpdating = false
        }
    }
}
