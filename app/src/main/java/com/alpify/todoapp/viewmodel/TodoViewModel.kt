package com.alpify.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpify.todoapp.data.Todo
import com.alpify.todoapp.data.TodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    val todos: StateFlow<List<Todo>> = repository.allTodos.map {
        it.sortedByDescending { todo -> todo.id }
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTodo(title: String) = viewModelScope.launch {
        repository.addTodo(Todo(title = title))
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch {
        repository.deleteTodo(todo)
    }

    fun toggleDone(todo: Todo) = viewModelScope.launch {
        repository.updateTodo(todo.copy(isDone = !todo.isDone))
    }
}