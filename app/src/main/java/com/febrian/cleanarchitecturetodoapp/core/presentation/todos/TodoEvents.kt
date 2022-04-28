package com.febrian.cleanarchitecturetodoapp.core.presentation.todos

import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.Todo
import com.febrian.cleanarchitecturetodoapp.core.domain.utils.LayoutType
import com.febrian.cleanarchitecturetodoapp.core.domain.utils.TodoOrder

sealed class TodoEvents {
    data class Order(val todoOrder: TodoOrder) : TodoEvents()
    data class DeleteTodo(val todo: Todo) : TodoEvents()
    data class SetLayoutType(val type: LayoutType, val todoOrder: TodoOrder) : TodoEvents()
    object RestoreTodo : TodoEvents()
    object ToggleOrderSection : TodoEvents()
}