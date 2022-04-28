package com.febrian.cleanarchitecturetodoapp.core.data.repository

import com.febrian.cleanarchitecturetodoapp.core.data.source.TodoDao
import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.Todo
import com.febrian.cleanarchitecturetodoapp.core.domain.repository.ITodoRepository
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val dao : TodoDao) : ITodoRepository {
    override fun getTodos(): Flow<List<Todo>> = dao.getTodos()

    override suspend fun getTodoById(id: Int): Todo? = dao.getTodoById(id)

    override suspend fun insertTodo(todo: Todo) = dao.insertTodo(todo)

    override suspend fun deleteTodo(todo: Todo) = dao.deleteTodo(todo)
}