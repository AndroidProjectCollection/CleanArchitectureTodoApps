package com.febrian.cleanarchitecturetodoapp.core.presentation.add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.InvalidTodoException
import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.Todo
import com.febrian.cleanarchitecturetodoapp.core.domain.usecase.TodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val todoUseCase: TodoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _todoTitle = mutableStateOf(TodoTextFieldState(hint = "Enter Title...."))
    val todoTitle: State<TodoTextFieldState> = _todoTitle

    private val _todoContent = mutableStateOf(TodoTextFieldState(hint = "Enter Content...."))
    val todoContent: State<TodoTextFieldState> = _todoContent

    private val _todoColor = mutableStateOf(Todo.todoColors.random().toArgb())
    val todoColor: State<Int> = _todoColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTodoId: Int? = null

    init {
        savedStateHandle.get<Int>("todoId")?.let { todoId ->
            if (todoId != -1) {
                viewModelScope.launch {
                    todoUseCase.getTodo(todoId)?.also { todo ->
                        currentTodoId = todo.id
                        _todoTitle.value = todoTitle.value.copy(
                            text = todo.title,
                            isHintVisible = false
                        )
                        _todoContent.value = _todoContent.value.copy(
                            text = todo.content,
                            isHintVisible = false
                        )
                        _todoColor.value = todo.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditTodoEvent) {
        when (event) {
            is AddEditTodoEvent.EnteredTitle -> {
                _todoTitle.value = todoTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditTodoEvent.ChangeTitleFocus -> {
                _todoTitle.value = _todoTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _todoTitle.value.text.isBlank()
                )
            }
            is AddEditTodoEvent.EnteredContent -> {
                _todoContent.value = _todoContent.value.copy(
                    text = event.value
                )
            }
            is AddEditTodoEvent.ChangeContentFocus -> {
                _todoContent.value = todoContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _todoContent.value.text.isBlank()
                )
            }
            is AddEditTodoEvent.ChangeColor -> {
                _todoColor.value = event.color
            }
            is AddEditTodoEvent.SaveTodo -> {
                viewModelScope.launch {
                    try {
                        todoUseCase.addTodo(
                            Todo(
                                title = todoTitle.value.text,
                                content = todoContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = todoColor.value,
                                id = currentTodoId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidTodoException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }

}