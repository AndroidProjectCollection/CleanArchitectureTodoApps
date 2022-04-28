package com.febrian.cleanarchitecturetodoapp.core.data.source

import androidx.room.*
import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getTodos(): Flow<List<Todo>>

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getTodoById(id: Int): Todo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)
}