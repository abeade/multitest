package com.github.jeremyrempel.unsplash

import com.github.jeremyrempel.unsplash.db.Database
import com.github.jeremyrempel.unsplash.db.Schema
import com.squareup.sqldelight.android.AndroidSqliteDriver

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        Database.dbSetup(AndroidSqliteDriver(Schema, this))
    }
}
