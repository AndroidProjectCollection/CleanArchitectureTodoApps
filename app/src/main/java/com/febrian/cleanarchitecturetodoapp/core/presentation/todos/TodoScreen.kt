package com.febrian.cleanarchitecturetodoapp.core.presentation.todos

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.febrian.cleanarchitecturetodoapp.core.domain.utils.LayoutType
import com.febrian.cleanarchitecturetodoapp.core.presentation.todos.components.OrderSection
import com.febrian.cleanarchitecturetodoapp.core.presentation.todos.components.TodoItem
import com.febrian.cleanarchitecturetodoapp.core.presentation.utils.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true)
@ExperimentalAnimationApi
@Composable
fun TodoScreen(
    navController: NavController = rememberNavController(),
    viewModel: TodosViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditTodoScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Todo")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your Todo",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(TodoEvents.ToggleOrderSection)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort"
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    todoOrder = state.todoOrder,
                    layoutType = state.todoLayoutType,
                    onOrderChange = { viewModel.onEvent(TodoEvents.Order(it)) },
                    onLayoutChange = {
                        viewModel.onEvent(
                            TodoEvents.SetLayoutType(
                                it,
                                state.todoOrder
                            )
                        )
                    }
                )

            }
            Spacer(modifier = Modifier.height(16.dp))

            SetLayout(
                state = state,
                navController = navController,
                viewModel = viewModel,
                scope = scope,
                scaffoldState = scaffoldState
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SetLayout(
    state: TodoState,
    navController: NavController,
    viewModel: TodosViewModel,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {

    if (state.todoLayoutType is LayoutType.List) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.todos) { todo ->
                TodoItem(
                    todo = todo,
                    navController = navController,
                    onDeleteClick = {
                        viewModel.onEvent(TodoEvents.DeleteTodo(todo))
                        scope.launch {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Todo deleted",
                                actionLabel = "Undo"
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.onEvent(TodoEvents.RestoreTodo)
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    } else {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                0.dp
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(state.todos) { todo ->
                TodoItem(
                    todo = todo,
                    navController = navController,
                    onDeleteClick = {
                        viewModel.onEvent(TodoEvents.DeleteTodo(todo))
                        scope.launch {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Todo deleted",
                                actionLabel = "Undo"
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.onEvent(TodoEvents.RestoreTodo)
                            }
                        }
                    }
                )
            }
        }
    }
}