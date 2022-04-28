package com.febrian.cleanarchitecturetodoapp.core.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    abstract val todoDao: TodoDao

    companion object {
        const val DATABASE_NAME = "Todos"
    }
}