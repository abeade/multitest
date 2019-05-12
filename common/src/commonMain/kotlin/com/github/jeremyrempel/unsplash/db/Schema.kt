package com.github.jeremyrempel.unsplash.db

import com.github.jeremyrempel.unsplash.Date
import com.github.jeremyrempel.unsplash.DateAdapter
import com.squareup.sqldelight.db.SqlDriver

fun createQueryWrapper(driver: SqlDriver): TestDb {
    return TestDb(
        driver = driver,
        pictureAdapter = Picture.Adapter(
            dateAdapter = DateAdapter()
        )
    )
}

object Schema : SqlDriver.Schema by TestDb.Schema {
    override fun create(driver: SqlDriver) {
        TestDb.Schema.create(driver)

        // Seed data time!
        createQueryWrapper(driver).apply {
            pictureQueries.insertPicture(
                "Hello world!", "https://cdn-images-1.medium.com/max/2600/1*0KFB17_NGTPB0XWyc4BSgQ.jpeg",  Date(1985, 5, 16)
            )
        }
    }
}
