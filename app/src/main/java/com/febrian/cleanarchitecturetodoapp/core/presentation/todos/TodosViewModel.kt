package com.febrian.cleanarchitecturetodoapp.core.presentation.todos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.Todo
import com.febrian.cleanarchitecturetodoapp.core.domain.usecase.TodoUseCase
import com.febrian.cleanarchitecturetodoapp.core.domain.utils.OrderType
import com.febrian.cleanarchitecturetodoapp.core.domain.utils.TodoOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(
    private val todoUseCase: TodoUseCase
) : ViewModel() {

    private val _state = mutableStateOf(TodoState())
    val state: State<TodoState> = _state

    private var recentlyDeletedTodo: Todo? = null

    private var getTodosJob: Job? = null

    init {
        getTodos(TodoOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: TodoEvents) {
        when (event) {
            is TodoEvents.Order -> {
                if (state.value.todoOrder::class == event.todoOrder::class &&
                    state.value.todoOrder.orderType == event.todoOrder.orderType
                ) {
                    return
                }
                getTodos(event.todoOrder)
            }
            is TodoEvents.DeleteTodo -> {
                viewModelScope.launch {
                    todoUseCase.deleteTodo(event.todo)
                    recentlyDeletedTodo = event.todo
                }
            }
            is TodoEvents.RestoreTodo -> {
                viewModelScope.launch {
                    todoUseCase.addTodo(recentlyDeletedTodo ?: return@launch)
                    recentlyDeletedTodo = null
                }
            }
            is TodoEvents.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }

            is TodoEvents.SetLayoutType -> {
                _state.value = TodoState(todoLayoutType = event.type)
                getTodos(event.todoOrder)
            }
        }
    }

    private fun getTodos(todoOrder: TodoOrder) {
        getTodosJob?.cancel()
        getTodosJob = todoUseCase.getTodos(todoOrder)
            .onEach { todos ->
                _state.value = state.value.copy(
                    todos = todos,
                    todoOrder = todoOrder
                )
            }
            .launchIn(viewModelScope)
    }
}