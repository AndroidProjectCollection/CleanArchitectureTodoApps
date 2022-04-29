package com.febrian.cleanarchitecturetodoapp.core.domain.usecase

import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.Todo
import com.febrian.cleanarchitecturetodoapp.core.domain.repository.ITodoRepository

class GetTodoById(private val repository: ITodoRepository) {
    suspend operator fun invoke(id: Int): Todo? {
        return repository.getTodoById(id)
    }
}