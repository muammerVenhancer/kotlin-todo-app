package com.example.todoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoapp.ui.todo.TodoListScreen
import com.example.todoapp.ui.todo.TodoEntryDestination
import com.example.todoapp.ui.todo.TodoEntryScreen
import com.example.todoapp.ui.todo.TodoListDestination

@Composable
fun TodoNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = TodoListDestination.route,
        modifier = modifier
    ) {
        composable(route = TodoListDestination.route) {
            TodoListScreen(
                navigateToTodoEntry = { navController.navigate(TodoEntryDestination.route) }
            )
        }

        composable(route = TodoEntryDestination.route) {
            TodoEntryScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}