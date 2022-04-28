package com.febrian.cleanarchitecturetodoapp.core.data.source.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.febrian.cleanarchitecturetodoapp.ui.theme.*

@Entity
data class Todo(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val todoColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidTodoException(message: String) : Exception(message)
