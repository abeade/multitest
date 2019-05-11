package com.github.jeremyrempel.unsplash

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import kotlin.native.concurrent.AtomicReference
import kotlin.native.concurrent.freeze

object Db {
    private val driverRef = AtomicReference<SqlDriver?>(null)
    private val dbRef = AtomicReference<TestDb?>(null)

    internal fun dbSetup(driver: SqlDriver) {
        val db = createQueryWrapper(driver)
        driverRef.value = driver.freeze()
        dbRef.value = db.freeze()
    }

    internal fun dbClear() {
        driverRef.value!!.close()
        dbRef.value = null
        driverRef.value = null
    }

    //Called from Swift
    @Suppress("unused")
    fun defaultDriver() {
        dbSetup(NativeSqliteDriver(Schema, "sampledb"))
    }

    val instance: TestDb
        get() = dbRef.value!!
}
