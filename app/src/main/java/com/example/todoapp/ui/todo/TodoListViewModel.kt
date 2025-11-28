package com.example.todoapp.ui.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Todo
import com.example.todoapp.data.TodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TodoListViewModel(private val todoRepository: TodoRepository) : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val todoListUiState: StateFlow<TodoListUiState> = todoRepository.getAllTodos().map { TodoListUiState(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = TodoListUiState()
    )

    suspend fun toggleTodo(todo: Todo) {
        todoRepository.updateTodo(todo.copy(isDone = !todo.isDone))
    }

    suspend fun deleteTodo(todo: Todo) {
        todoRepository.deleteTodo(todo)
    }
}

data class TodoListUiState(val todoList: List<Todo> = listOf())