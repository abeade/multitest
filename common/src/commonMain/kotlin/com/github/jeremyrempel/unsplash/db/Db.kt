package com.github.jeremyrempel.unsplash.db

import com.squareup.sqldelight.db.SqlDriver

interface Db {
    val ready: Boolean
    val instance: TestDb

    fun dbSetup(driver: SqlDriver)
    fun dbClear()
}
