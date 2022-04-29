package com.febrian.cleanarchitecturetodoapp.core.domain.usecase

import com.febrian.cleanarchitecturetodoapp.core.data.repository.FakeTodoRepository
import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.Todo
import com.febrian.cleanarchitecturetodoapp.core.domain.utils.OrderType
import com.febrian.cleanarchitecturetodoapp.core.domain.utils.TodoOrder
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetTodosTest {

    private lateinit var getTodos: GetTodos
    private lateinit var fakeTodoRepository: FakeTodoRepository

    @Before
    fun setup() {
        fakeTodoRepository = FakeTodoRepository()
        getTodos = GetTodos(fakeTodoRepository)

        val todoInsert = mutableListOf<Todo>()
        ('a'..'z').forEachIndexed { index, c ->
            todoInsert.add(
                Todo(
                    title = c.toString(),
                    content = c.toString(),
                    color = index,
                    timestamp = index.toLong()
                )
            )
        }

        todoInsert.shuffle()

        runBlocking {
            todoInsert.forEach {
                fakeTodoRepository.insertTodo(it)
            }
        }
    }

    @Test
    fun `Order todos by ascending`() = runBlocking {
        val todo = getTodos(TodoOrder.Title(OrderType.Ascending)).first()

        for (i in 0..todo.size - 2) {
            assertThat(todo[i].title).isLessThan(todo[i + 1].title)
        }

    }

    @Test
    fun `Order todos by title descending`() = runBlocking {
        val todos = getTodos(TodoOrder.Title(OrderType.Descending)).first()

        for (i in 0..todos.size - 2) {
            assertThat(todos[i].title).isGreaterThan(todos[i + 1].title)
        }
    }

    @Test
    fun `Order todos by date ascending`() = runBlocking {
        val todos = getTodos(TodoOrder.Date(OrderType.Ascending)).first()

        for (i in 0..todos.size - 2) {
            assertThat(todos[i].timestamp).isLessThan(todos[i + 1].timestamp)
        }
    }

    @Test
    fun `Order todos by date descending`() = runBlocking {
        val todos = getTodos(TodoOrder.Date(OrderType.Descending)).first()

        for (i in 0..todos.size - 2) {
            assertThat(todos[i].timestamp).isGreaterThan(todos[i + 1].timestamp)
        }
    }

    @Test
    fun `Order notes by color ascending`() = runBlocking {
        val todos = getTodos(TodoOrder.Color(OrderType.Ascending)).first()

        for (i in 0..todos.size - 2) {
            assertThat(todos[i].color).isLessThan(todos[i + 1].color)
        }
    }

    @Test
    fun `Order notes by color descending`() = runBlocking {
        val notes = getTodos(TodoOrder.Color(OrderType.Descending)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isGreaterThan(notes[i + 1].color)
        }
    }

}