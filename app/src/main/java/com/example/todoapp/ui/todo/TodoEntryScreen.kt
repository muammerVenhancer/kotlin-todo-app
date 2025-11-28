package com.example.todoapp.ui.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.R
import com.example.todoapp.TodoTopAppBar
import com.example.todoapp.ui.AppViewModelProvider
import com.example.todoapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object TodoEntryDestination : NavigationDestination {
    override val route = "todo_entry"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEntryScreen(
    navigateBack: () -> Unit,
    viewModel: TodoEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TodoTopAppBar(
                title = stringResource(TodoListDestination.titleRes),
                scrollBehavior = null,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
    ) { innerPadding ->
        TodoEntryBody(
            viewModel = viewModel,
            onSave = { coroutineScope.launch { viewModel.saveTodo(); navigateBack() } },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoEntryBody(
    viewModel: TodoEntryViewModel,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.todoUiState

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedTextField(
            value = uiState.title,
            onValueChange = { viewModel.updateUiState(uiState.copy(title = it)) },
            singleLine = true,
            maxLines = 1,
            shape = MaterialTheme.shapes.small,
            label = { Text(text = stringResource(R.string.title)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (uiState.title.isNotBlank()) {
                        onSave()
                    }
                }
            )
        )

        Button(
            onClick = onSave,
            enabled = uiState.title.isNotBlank(),
            modifier = Modifier.fillMaxWidth().height(50.dp)

        ) {
            Text(text = stringResource(R.string.save), style = MaterialTheme.typography.titleMedium)
        }
    }
}