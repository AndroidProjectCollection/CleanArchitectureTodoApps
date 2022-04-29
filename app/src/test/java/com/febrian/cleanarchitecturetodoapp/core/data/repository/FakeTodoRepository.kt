package com.febrian.cleanarchitecturetodoapp.core.data.repository

import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.Todo
import com.febrian.cleanarchitecturetodoapp.core.domain.repository.ITodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTodoRepository : ITodoRepository {

    private val todos = mutableListOf<Todo>()

    override fun getTodos(): Flow<List<Todo>> {
        return flow { emit(todos) }
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return todos.find { it.id == id }
    }

    override suspend fun insertTodo(todo: Todo) {
        todos.add(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        todos.remove(todo)
    }
}