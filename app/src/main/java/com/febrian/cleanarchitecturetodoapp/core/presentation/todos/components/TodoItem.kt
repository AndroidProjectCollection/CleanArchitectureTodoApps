package com.febrian.cleanarchitecturetodoapp.core.presentation.todos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.Todo
import com.febrian.cleanarchitecturetodoapp.core.presentation.utils.Screen

@Preview( showBackground = true)
@Composable
fun TodoItem(
    todo: Todo = Todo("Todo","Todo",0,1),
    navController: NavController = rememberNavController(),
    onDeleteClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, top = 4.dp, bottom = 4.dp, end = 4.dp)
            .wrapContentHeight()
            .clip(
                shape = RoundedCornerShape(12.dp)
            )
            .background(color = Color(todo.color))
            .clickable {
                navController.navigate(
                    Screen.AddEditTodoScreen.route +
                            "?todoId=${todo.id}&todoColor=${todo.color}"
                )
            },

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            Text(
                text = todo.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = todo.content,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Todo",
                tint = MaterialTheme.colors.onSurface
            )
        }
    }

}