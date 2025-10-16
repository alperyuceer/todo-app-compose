package com.alpify.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.alpify.todoapp.data.TodoDatabase
import com.alpify.todoapp.data.TodoRepository
import com.alpify.todoapp.ui.TodoScreen
import com.alpify.todoapp.ui.theme.TodoAppTheme
import com.alpify.todoapp.viewmodel.TodoViewModel
import com.alpify.todoapp.viewmodel.TodoViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        val db = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            "todo_db"
        ).build()

        val repository = TodoRepository(db.todoDao())
        val factory = TodoViewModelFactory(repository)
        setContent {
            val viewModel: TodoViewModel = viewModel(factory = factory)
            TodoScreen(viewModel)

        }
    }
}

