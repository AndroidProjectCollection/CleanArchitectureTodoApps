package com.febrian.cleanarchitecturetodoapp.core.presentation.add_edit

data class TodoTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
