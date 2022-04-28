package com.febrian.cleanarchitecturetodoapp.core.presentation.add_edit

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.febrian.cleanarchitecturetodoapp.core.data.source.entity.Todo
import com.febrian.cleanarchitecturetodoapp.core.presentation.add_edit.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditTodoScreen(
    navController: NavController,
    todoColor: Int,
    addEditViewModel: AddEditViewModel = hiltViewModel()
) {
    val titleState = addEditViewModel.todoTitle.value
    val contentState = addEditViewModel.todoContent.value

    val scaffoldState = rememberScaffoldState()

    val todoBackgroundAnimatable = remember {
        Animatable(
            Color(if (todoColor != -1) todoColor else addEditViewModel.todoColor.value)
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        addEditViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    addEditViewModel.onEvent(AddEditTodoEvent.SaveTodo)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(todoBackgroundAnimatable.value)
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Todo.todoColors.forEach { color ->
                    val colorInt = color.toArgb()

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (addEditViewModel.todoColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    todoBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                addEditViewModel.onEvent(AddEditTodoEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                    addEditViewModel.onEvent(AddEditTodoEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    addEditViewModel.onEvent(AddEditTodoEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {
                    addEditViewModel.onEvent(AddEditTodoEvent.EnteredContent(it))
                },
                onFocusChange = {
                    addEditViewModel.onEvent(AddEditTodoEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}