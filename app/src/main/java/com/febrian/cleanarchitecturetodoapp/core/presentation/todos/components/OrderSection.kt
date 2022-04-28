package com.febrian.cleanarchitecturetodoapp.core.presentation.todos.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.febrian.cleanarchitecturetodoapp.core.domain.utils.LayoutType
import com.febrian.cleanarchitecturetodoapp.core.domain.utils.OrderType
import com.febrian.cleanarchitecturetodoapp.core.domain.utils.TodoOrder

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    todoOrder: TodoOrder = TodoOrder.Date(OrderType.Descending),
    layoutType: LayoutType = LayoutType.List,
    onOrderChange: (TodoOrder) -> Unit = {},
    onLayoutChange: (LayoutType) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = todoOrder is TodoOrder.Title,
                onSelect = { onOrderChange(TodoOrder.Title(todoOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = todoOrder is TodoOrder.Date,
                onSelect = { onOrderChange(TodoOrder.Date(todoOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                selected = todoOrder is TodoOrder.Color,
                onSelect = { onOrderChange(TodoOrder.Color(todoOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = todoOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(todoOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = todoOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(todoOrder.copy(OrderType.Descending))
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "List",
                selected = layoutType is LayoutType.List,
                onSelect = { onLayoutChange(LayoutType.List) })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Grid",
                selected = layoutType is LayoutType.Grid,
                onSelect = { onLayoutChange(LayoutType.Grid) })
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewOrderSection() {
    OrderSection(onOrderChange = {})
}

