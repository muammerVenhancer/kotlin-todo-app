package com.example.todoapp.data

import kotlinx.coroutines.flow.Flow

class OfflineTodoRepository(private val todoDao: TodoDao): TodoRepository {
    override fun getAllTodos(): Flow<List<Todo>> = todoDao.getAll()

    override suspend fun insertTodo(todo: Todo) = todoDao.insert(todo)

    override suspend fun updateTodo(todo: Todo) = todoDao.update(todo)

    override suspend fun deleteTodo(todo: Todo) = todoDao.delete(todo)
}