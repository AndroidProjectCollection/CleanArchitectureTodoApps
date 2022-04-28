package com.febrian.cleanarchitecturetodoapp.core.domain.repository

import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.Todo
import kotlinx.coroutines.flow.Flow

interface ITodoRepository {
    fun getTodos(): Flow<List<Todo>>

    suspend fun getTodoById(id: Int): Todo?

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)
}