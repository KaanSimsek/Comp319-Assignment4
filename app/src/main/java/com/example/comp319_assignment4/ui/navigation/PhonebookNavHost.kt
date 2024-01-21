package com.example.comp319_assignment4.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.comp319_assignment4.ui.people.PeopleDetailsDestination
import com.example.comp319_assignment4.ui.people.PeopleDetailsScreen
import com.example.comp319_assignment4.ui.people.PeopleEditDestination
import com.example.comp319_assignment4.ui.people.PeopleEditScreen
import com.example.comp319_assignment4.ui.people.PeopleEntryDestination
import com.example.comp319_assignment4.ui.people.PeopleEntryScreen

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
