import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.healthstudio.data.UserSettings
import com.example.healthstudio.data.UserSettingsDao
import com.example.healthstudio.data.HealthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class HealthViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HealthViewModel
    private val userSettingsDao = mock<UserSettingsDao>()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HealthViewModel(mock(), userSettingsDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testUpdateUsername_updatesCorrectly() = runTest {
        val initialSettings = UserSettings(username = "default")
        whenever(userSettingsDao.getUserSettings()).thenReturn(initialSettings)

        viewModel.updateUsername("Oscar")
        testDispatcher.scheduler.advanceUntilIdle()

        verify(userSettingsDao).updateUserSettings(check {
            assertEquals("Oscar", it.username)
        })
    }
}