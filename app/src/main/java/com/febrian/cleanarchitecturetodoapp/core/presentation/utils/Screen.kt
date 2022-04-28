package com.febrian.cleanarchitecturetodoapp.core.presentation.utils


sealed class Screen(val route: String) {
    object TodoScreen : Screen("todo_screen")
    object AddEditTodoScreen : Screen("add_edit_todo_screen")
}
