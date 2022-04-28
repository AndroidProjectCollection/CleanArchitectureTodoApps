package com.febrian.cleanarchitecturetodoapp.core.domain.usecase

data class TodoUseCase(
    val getTodos: GetTodos,
    val deleteTodo: DeleteTodo,
    val addTodo: AddTodo,
    val getTodo: GetTodoById
)
