package com.github.jeremyrempel.unsplash.db

import com.github.jeremyrempel.unsplash.Date
import com.github.jeremyrempel.unsplash.DateAdapter
import com.squareup.sqldelight.db.SqlDriver

fun createQueryWrapper(driver: SqlDriver): TestDb {
    return TestDb(
        driver = driver,
        playerAdapter = Player.Adapter(
            birth_dateAdapter = DateAdapter()
        )
    )
}

object Schema : SqlDriver.Schema by TestDb.Schema {
    override fun create(driver: SqlDriver) {
        TestDb.Schema.create(driver)

        // Seed data time!
        createQueryWrapper(driver).apply {
            playerQueries.insertPlayer(
                "Corey", "Perry", 10, 30, 210F, Date(1985, 5, 16)
            )
            playerQueries.insertPlayer(
                "Ryan", "Getzlaf", 15, 30, 221F, Date(1985, 5, 10)
            )
            playerQueries.insertPlayer(
                "Sidney", "Crosby", 87, 28, 200F, Date(1987, 8, 7)
            )
            playerQueries.insertPlayer(
                "Erik", "Karlsson", 65, 28, 190F, Date(1990, 5, 31)
            )
            playerQueries.insertPlayer(
                "Joe", "Pavelski", 8, 31, 194F, Date(1984, 7, 18)
            )
        }
    }
}
