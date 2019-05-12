package com.github.jeremyrempel.unsplash

import android.content.Context
import com.github.jeremyrempel.unsplash.db.Database
import com.github.jeremyrempel.unsplash.db.TestDb
import com.github.jeremyrempel.unsplash.db.TestDb.Companion.Schema
import com.squareup.sqldelight.android.AndroidSqliteDriver

fun Database.getInstance(context: Context): TestDb {
    if (!ready) {
        dbSetup(AndroidSqliteDriver(Schema, context))
    }
    return instance
}
