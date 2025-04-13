package com.example.healthstudio

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.healthstudio.ui.theme.HealthStudioTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccountPageTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            HealthStudioTheme {
                AccountPage()  // 确保测试的是这个页面
            }
        }
    }

    @Test
    fun userInfoFieldsAreDisplayed() {
        composeTestRule.onNodeWithTag("usernameField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("genderField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ageField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("saveButton").assertIsDisplayed()
    }

    @Test
    fun saveUserInfoUpdatesDisplayedValues() {
        composeTestRule.onNodeWithTag("usernameField").performTextInput("Test User")
        composeTestRule.onNodeWithTag("genderField").performTextInput("Female")
        composeTestRule.onNodeWithTag("ageField").performTextInput("18")

        composeTestRule.onNodeWithTag("saveButton").performClick()

        // 检查是否更新
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithText("User Name: Test User")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
    }
}
