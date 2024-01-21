package com.example.comp319_assignment4

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.comp319_assignment4.ui.home.HomeScreen
import com.example.comp319_assignment4.ui.people.PeopleDetailsDestination
import com.example.comp319_assignment4.ui.people.PeopleDetailsScreen
import com.example.comp319_assignment4.ui.people.PeopleEditDestination
import com.example.comp319_assignment4.ui.people.PeopleEditScreen
import com.example.comp319_assignment4.ui.people.PeopleEntryDestination
import com.example.comp319_assignment4.ui.people.PeopleEntryScreen


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PhonebookApp(navController: NavHostController = rememberNavController()) {
    PhonebookNavHost(navController = navController)
}

@Composable
fun PhonebookNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable(route = "home") {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(PeopleEntryDestination.route) },
                navigateToItemUpdate = {
                    navController.navigate("${PeopleDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = PeopleEntryDestination.route) {
            PeopleEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = PeopleDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(PeopleDetailsDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            PeopleDetailsScreen(
                navigateToEditItem = { navController.navigate("${PeopleEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = PeopleEditDestination.routeWithArgs,
            arguments = listOf(navArgument(PeopleEditDestination.peopleIdArg) {
                type = NavType.IntType
            })
        ) {
            PeopleEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhonebookTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}