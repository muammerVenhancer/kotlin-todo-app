package com.example.todoapp.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.Todo
import com.example.todoapp.data.TodoRepository

class TodoEntryViewModel(private val todoRepository: TodoRepository) : ViewModel() {
    var todoUiState by mutableStateOf(TodoUiState())
        private set

    fun updateUiState(newTodoUiState: TodoUiState) {
        todoUiState = newTodoUiState
    }

    fun isValidInput(): Boolean = todoUiState.title.isNotBlank()

    suspend fun saveTodo() {
        if (isValidInput()) {
            todoRepository.insertTodo(todoUiState.toTodo())
        }
    }
}

data class TodoUiState(
    val title: String = "",
    val isDone: Boolean = false
)

fun TodoUiState.toTodo(): Todo = Todo(
    title = title,
    isDone = isDone
)