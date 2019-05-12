package com.github.jeremyrempel.unsplash.presentation

import com.github.jeremyrempel.unsplash.Date
import com.github.jeremyrempel.unsplash.api.PhotoApi
import com.github.jeremyrempel.unsplash.db.TestDb
import com.github.jeremyrempel.unsplash.di.kodein
import com.github.jeremyrempel.unsplash.presentation.model.PictureData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.erased.instance
import kotlin.coroutines.CoroutineContext

class PhotoPresenter(
    uiContext: CoroutineContext,
    private val view: PhotoView
) : CoroutinePresenter(uiContext, view), PhotoActions {
    private val api: PhotoApi by kodein.instance()
    private val db: TestDb by kodein.instance()

    override fun onRequestData() = updateData()

    override fun onViewReady() = loadLastData()

    private fun loadLastData() {
        view.isUpdating = true

        GlobalScope.launch(coroutineContext) {
            // SQLDelight usage sample from multi platform common code
            db.pictureQueries.selectAll().executeAsList().first().let {
                view.onUpdate(PictureData(it.description, it.url))
            }
        }.invokeOnCompletion {
            view.isUpdating = false
        }
    }

    private fun updateData() {
        view.isUpdating = true

        GlobalScope.launch(coroutineContext) {
            val response = api.getRandom()
            launch {
                db.transaction {
                    db.pictureQueries.deleteAll()
                    db.pictureQueries.insertPicture(response.description, response.urls.thumb, Date(2019, 1, 1))
                }
            }
            view.onUpdate(PictureData(response.description, response.urls.thumb))


        }.invokeOnCompletion {
            view.isUpdating = false
        }
    }
}
