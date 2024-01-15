package com.mujeeb.weatherapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mujeeb.weatherapp.presentation.screen.MainScreen
import com.mujeeb.weatherapp.presentation.screen.DetailsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier

) {
    NavHost(
        navController = navController,
        startDestination = Main.route,
        modifier = modifier
    ) {

        composable(route = Main.route) {
            MainScreen(
                modifier = modifier,
                onShowDetailsScreen = { id ->
                    navController.navigate(Details.route + "/${id}")
                }

            )
        }
        composable(
            route = Details.route + "/{locationId}",
            arguments = listOf(navArgument("locationId") { type = NavType.StringType })
        ) { navBackStackEntry ->

            val id = navBackStackEntry.arguments?.getString("locationId")
            id?.let {
                DetailsScreen(modifier = modifier, id = it)
            }

        }
    }
}



