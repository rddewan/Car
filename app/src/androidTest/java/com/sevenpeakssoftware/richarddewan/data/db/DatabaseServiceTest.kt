package com.sevenpeakssoftware.richarddewan.data.db


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.sevenpeakssoftware.richarddewan.data.local.dao.ArticleDao
import com.sevenpeakssoftware.richarddewan.data.local.db.DatabaseService
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
open class DatabaseServiceTest {


    /*
   run architecture component related background jobs in same thread for testing
    */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var databaseService: DatabaseService

    lateinit var articleDao: ArticleDao

    @Before
    fun setup() {
        databaseService = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            DatabaseService::class.java
        ).allowMainThreadQueries()
            .build()

        articleDao = databaseService.articleDao()
        println("Database created")

    }

    @After
    fun closeDb() {
        databaseService.close()
        println("Database closed")
    }


}