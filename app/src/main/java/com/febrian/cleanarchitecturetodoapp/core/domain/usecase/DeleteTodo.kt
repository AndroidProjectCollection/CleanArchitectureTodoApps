package com.febrian.cleanarchitecturetodoapp.core.domain.usecase

import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.Todo
import com.febrian.cleanarchitecturetodoapp.core.domain.repository.ITodoRepository

class DeleteTodo(
    private val repository: ITodoRepository
) {

    suspend operator fun invoke(todo: Todo) {
        repository.deleteTodo(todo)
    }
}