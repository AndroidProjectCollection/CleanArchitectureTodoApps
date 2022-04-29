package com.febrian.cleanarchitecturetodoapp.core.domain.usecase

import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.InvalidTodoException
import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.Todo
import com.febrian.cleanarchitecturetodoapp.core.domain.repository.ITodoRepository

class AddTodo(private val repository: ITodoRepository) {

    @Throws(InvalidTodoException::class)
    suspend operator fun invoke(todo : Todo){
        if(todo.title.isBlank()){
            throw InvalidTodoException("The title can't be empty!")
        }

        if(todo.content.isBlank()){
            throw InvalidTodoException("The content can't be empty!")
        }

        repository.insertTodo(todo)
    }
}