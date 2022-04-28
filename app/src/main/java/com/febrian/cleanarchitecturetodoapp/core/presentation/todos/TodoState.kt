package com.febrian.cleanarchitecturetodoapp.core.presentation.todos

import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.Todo
import com.febrian.cleanarchitecturetodoapp.core.domain.utils.LayoutType
import com.febrian.cleanarchitecturetodoapp.core.domain.utils.OrderType
import com.febrian.cleanarchitecturetodoapp.core.domain.utils.TodoOrder

data class TodoState(
    val todos: List<Todo> = emptyList(),
    val todoOrder: TodoOrder = TodoOrder.Date(OrderType.Descending),
    val todoLayoutType: LayoutType = LayoutType.List,
    val isOrderSectionVisible: Boolean = false
)