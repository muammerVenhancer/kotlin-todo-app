package com.example.todoapp.ui.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.R
import com.example.todoapp.TodoTopAppBar
import com.example.todoapp.data.Todo
import com.example.todoapp.ui.AppViewModelProvider
import com.example.todoapp.ui.navigation.NavigationDestination
import com.example.todoapp.ui.theme.TodoAppTheme
import kotlinx.coroutines.launch

object TodoListDestination : NavigationDestination {
    override val route = "todo_list"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier,
    navigateToTodoEntry: () -> Unit = {},
    viewModel: TodoListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val todoListUiState by viewModel.todoListUiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TodoTopAppBar(
                title = stringResource(TodoListDestination.titleRes),
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTodoEntry,
                modifier = Modifier
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    ) { innerPadding ->
        ListBody(
            todoList = todoListUiState.todoList,
            onCheck = { coroutineScope.launch { viewModel.toggleTodo(it) } },
            onDelete = { coroutineScope.launch { viewModel.deleteTodo(it) } },
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 8.dp)
                .fillMaxSize()
        )
    }
}

@Composable
private fun ListBody(
    todoList: List<Todo>,
    modifier: Modifier = Modifier,
    onCheck: (Todo) -> Unit = {},
    onDelete: (Todo) -> Unit = {},
) {
    if (todoList.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            Text(text = "No todos", style = MaterialTheme.typography.titleLarge)
        }
    } else {
        TodoList(
            todoList = todoList,
            modifier = modifier.padding(horizontal = 16.dp),
            onCheck = onCheck,
            onDelete = onDelete
        )
    }
}

@Composable
private fun TodoItem(
    todo: Todo,
    modifier: Modifier = Modifier,
    onCheck: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onCheck,
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (todo.isDone) {
                Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "")
            }

            Text(
                text = todo.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium.copy(textDecoration = if (todo.isDone) TextDecoration.LineThrough else TextDecoration.None),
            )

            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview() {
    TodoAppTheme {
        TodoItem(todo = Todo(1, "Todo 1", true, System.currentTimeMillis()))
    }
}

@Composable
private fun TodoList(
    todoList: List<Todo>,
    modifier: Modifier = Modifier,
    onCheck: (Todo) -> Unit = {},
    onDelete: (Todo) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(items = todoList, key = { it.id }) { item ->
            TodoItem(todo = item, onCheck = { onCheck(item) }, onDelete = { onDelete(item) })
        }
    }
}