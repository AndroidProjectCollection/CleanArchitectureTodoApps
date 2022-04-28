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
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): ITodoRepository {
        return TodoRepository(db.todoDao)
    }

    @Provides
    @Singleton
    fun provideTodoUseCases(repository: ITodoRepository): TodoUseCase {
        return TodoUseCase(
            GetTodos(repository),
            DeleteTodo(repository),
            AddTodo(repository),
            GetTodoById(repository)
        )
    }
}