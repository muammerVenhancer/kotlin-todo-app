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

    private fun validateInput(uiState: TodoUiState): Boolean {
        return with(uiState) {
            title.isNotBlank()
        }
    }

    fun updateUiState(newTodoUiState: TodoUiState) {
        if (validateInput(newTodoUiState)) {
            todoUiState = newTodoUiState.copy(isDone = todoUiState.isDone)
        }
    }

    suspend fun saveTodo() {
        if (validateInput(todoUiState)) {
            todoRepository.insertTodo(todoUiState.toTodo())
        }
    }

}

data class TodoUiState(
    val title: String = "",
    val description: String = "",
    val isDone: Boolean = false
)

fun TodoUiState.toTodo(): Todo = Todo(
    title = title,
    isDone = isDone
)