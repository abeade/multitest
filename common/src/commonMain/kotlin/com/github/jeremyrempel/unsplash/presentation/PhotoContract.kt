package com.github.jeremyrempel.unsplash.presentation

import com.github.jeremyrempel.unsplash.presentation.model.PictureData

interface PhotoView : BaseView {
    var isUpdating: Boolean
    fun onUpdate(data: PictureData)
}

interface PhotoActions {
    fun onViewReady()
    fun onRequestData()
}
