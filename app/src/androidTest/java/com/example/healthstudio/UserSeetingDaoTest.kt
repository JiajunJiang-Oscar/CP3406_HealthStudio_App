package com.example.healthstudio

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.healthstudio.data.HealthDatabase
import com.example.healthstudio.data.UserSettings
import com.example.healthstudio.data.UserSettingsDao
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserSettingsDaoTest {

    private lateinit var db: HealthDatabase
    private lateinit var dao: UserSettingsDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, HealthDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.userSettingsDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun testInsertAndGetUserSettings() = runBlocking {
        val user = UserSettings(username = "Oscar", gender = "Male", age = 25)
        dao.updateUserSettings(user)

        val result = dao.getUserSettings()
        Assert.assertEquals("Oscar", result?.username)
        Assert.assertEquals("Male", result?.gender)
        Assert.assertEquals(25, result?.age)
    }
}