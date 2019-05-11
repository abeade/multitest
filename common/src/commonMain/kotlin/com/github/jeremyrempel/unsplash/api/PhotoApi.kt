package com.github.jeremyrempel.unsplash.api

import com.github.jeremyrempel.unsplash.data.PhotoResponse

interface PhotoApi {
    suspend fun getRandom(): PhotoResponse
}
