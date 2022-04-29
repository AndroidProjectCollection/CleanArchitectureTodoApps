package com.febrian.cleanarchitecturetodoapp.core.domain.usecase

import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.Todo
import com.febrian.cleanarchitecturetodoapp.core.domain.repository.ITodoRepository
import com.febrian.cleanarchitecturetodoapp.core.domain.utils.OrderType
import com.febrian.cleanarchitecturetodoapp.core.domain.utils.TodoOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTodos(private val repository: ITodoRepository) {
    operator fun invoke(
        todoOrder: TodoOrder = TodoOrder.Date(OrderType.Descending)
    ): Flow<List<Todo>> {
        return repository.getTodos().map { todos ->
            when (todoOrder.orderType) {
                is OrderType.Ascending -> {
                    when (todoOrder) {
                        is TodoOrder.Title -> todos.sortedBy { it.title.lowercase() }
                        is TodoOrder.Date -> todos.sortedBy { it.timestamp }
                        is TodoOrder.Color -> todos.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when (todoOrder) {
                        is TodoOrder.Title -> todos.sortedByDescending { it.title.lowercase() }
                        is TodoOrder.Date -> todos.sortedByDescending { it.timestamp }
                        is TodoOrder.Color -> todos.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}