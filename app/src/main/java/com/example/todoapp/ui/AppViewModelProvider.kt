package com.example.todoapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoapp.TodoApplication
import com.example.todoapp.ui.todo.TodoEntryViewModel
import com.example.todoapp.ui.todo.TodoListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TodoListViewModel(todoApplication().container.todoRepository)
        }

        initializer {
            TodoEntryViewModel(todoApplication().container.todoRepository)
        }
    }
}

fun CreationExtras.todoApplication(): TodoApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TodoApplication)