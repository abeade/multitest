package com.github.jeremyrempel.unsplash.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import kotlin.native.concurrent.AtomicReference
import kotlin.native.concurrent.freeze

actual object Database : Db {
    private val driverRef = AtomicReference<SqlDriver?>(null)
    private val dbRef = AtomicReference<TestDb?>(null)

    override fun dbSetup(driver: SqlDriver) {
        val db = createQueryWrapper(driver)
        driverRef.value = driver.freeze()
        dbRef.value = db.freeze()
    }

    override fun dbClear() {
        driverRef.value!!.close()
        dbRef.value = null
        driverRef.value = null
    }

    //Called from Swift
    @Suppress("unused")
    fun defaultDriver() {
        dbSetup(NativeSqliteDriver(Schema, "sampledb"))
    }

    override val ready: Boolean
        get() = driverRef.value != null

    override val instance: TestDb
        get() = dbRef.value!!
}
