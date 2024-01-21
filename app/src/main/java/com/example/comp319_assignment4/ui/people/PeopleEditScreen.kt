package com.example.comp319_assignment4.ui.people

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comp319_assignment4.PhonebookTopAppBar
import com.example.comp319_assignment4.ui.navigation.NavigationDestination
import com.example.comp319_assignment4.ui.theme.Comp319Assignment4Theme
import com.example.comp319_assignment4.ui.AppViewModelProvider
import kotlinx.coroutines.launch
import com.example.comp319_assignment4.R


object PeopleEditDestination : NavigationDestination {
    override val route = "item_edit"
    override val titleRes = R.string.edit_item_title
    const val peopleIdArg = "peopleId"
    val routeWithArgs = "$route/{$peopleIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PeopleEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            PhonebookTopAppBar(title = stringResource(PeopleEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp )
        },
        modifier = modifier
    ) { innerPadding ->
        PeopleEntryBody(
            peopleUiState = viewModel.peopleUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                // Note: If the user rotates the screen very fast, the operation may get cancelled
                // and the item may not be updated in the Database. This is because when config
                // change occurs, the Activity will be recreated and the rememberCoroutineScope will
                // be cancelled - since the scope is bound to composition.
                coroutineScope.launch {
                    viewModel.updatePeople()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PeopleEditScreenPreview() {
    Comp319Assignment4Theme {
        PeopleEditScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do nothing*/ })
    }
}
