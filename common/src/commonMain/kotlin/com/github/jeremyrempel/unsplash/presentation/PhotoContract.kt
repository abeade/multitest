package com.github.jeremyrempel.unsplash.presentation

import com.github.jeremyrempel.unsplash.data.PhotoResponse

interface PhotoView : BaseView {
    var isUpdating: Boolean
    fun onUpdate(data: PhotoResponse)
}

interface PhotoActions {
    fun onRequestData()
}
