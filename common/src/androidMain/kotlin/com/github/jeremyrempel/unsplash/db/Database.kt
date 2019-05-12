package com.github.jeremyrempel.unsplash.db

import com.squareup.sqldelight.db.SqlDriver

actual object Database : Db {
    private var driverRef: SqlDriver? = null
    private var dbRef: TestDb? = null

    override val ready:Boolean
        get() = driverRef != null

    override fun dbSetup(driver: SqlDriver) {
        val db = createQueryWrapper(driver)
        driverRef = driver
        dbRef = db
    }

    override fun dbClear() {
        driverRef!!.close()
        dbRef = null
        driverRef = null
    }

    override val instance: TestDb
        get() = dbRef!!
}
