package com.febrian.cleanarchitecturetodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.febrian.cleanarchitecturetodoapp.core.presentation.add_edit.AddEditTodoScreen
import com.febrian.cleanarchitecturetodoapp.core.presentation.add_edit.AddEditViewModel
import com.febrian.cleanarchitecturetodoapp.core.presentation.todos.TodoScreen
import com.febrian.cleanarchitecturetodoapp.core.presentation.utils.Screen
import com.febrian.cleanarchitecturetodoapp.ui.theme.CleanArchitectureTodoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureTodoAppTheme {

                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.TodoScreen.route
                    ) {
                        composable(route = Screen.TodoScreen.route) {
                            TodoScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditTodoScreen.route +
                                    "?todoId={todoId}&todoColor={todoColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "todoId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "todoColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color = it.arguments?.getInt("todoColor") ?: -1
                            AddEditTodoScreen(
                                navController = navController,
                                todoColor = color
                            )
                        }

                    }
                }
            }
        }

    }

}
