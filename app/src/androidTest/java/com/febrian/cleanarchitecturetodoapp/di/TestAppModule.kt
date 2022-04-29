package com.febrian.cleanarchitecturetodoapp.di

import android.app.Application
import androidx.room.Room
import com.febrian.cleanarchitecturetodoapp.core.data.repository.TodoRepository
import com.febrian.cleanarchitecturetodoapp.core.data.source.TodoDatabase
import com.febrian.cleanarchitecturetodoapp.core.domain.repository.ITodoRepository
import com.febrian.cleanarchitecturetodoapp.core.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): TodoDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            TodoDatabase::class.java,
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: TodoDatabase): ITodoRepository {
        return TodoRepository(db.todoDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: ITodoRepository): TodoUseCase {
        return TodoUseCase(
            getTodos = GetTodos(repository),
            deleteTodo = DeleteTodo(repository),
            addTodo  = AddTodo(repository),
            getTodo = GetTodoById(repository)
        )
    }
}