package com.github.jeremyrempel.unsplash

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver

fun Db.getInstance(context: Context): TestDb {
    if (!ready) {
        dbSetup(AndroidSqliteDriver(Schema, context))
    }
    return instance
}
