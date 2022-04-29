package com.febrian.cleanarchitecturetodoapp.core.todos

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.febrian.cleanarchitecturetodoapp.MainActivity
import com.febrian.cleanarchitecturetodoapp.core.presentation.todos.TodoScreen
import com.febrian.cleanarchitecturetodoapp.core.presentation.utils.Screen
import com.febrian.cleanarchitecturetodoapp.di.AppModule
import com.febrian.cleanarchitecturetodoapp.ui.theme.CleanArchitectureTodoAppTheme
import com.febrian.cleanarchitecturetodoapp.utils.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class TodoScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            CleanArchitectureTodoAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.TodoScreen.route
                ) {
                    composable(route = Screen.TodoScreen.route) {
                        TodoScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
    }
}