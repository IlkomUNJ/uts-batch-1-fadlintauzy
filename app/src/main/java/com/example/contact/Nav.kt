package com.example.contact

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun ContactApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") {
            HomeScreen(navController = navController)
        }

        composable("edit_contact/{contactId}", arguments = listOf(
            navArgument("contactId") { type = NavType.IntType }
        )) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId") ?: 0
            EditScreen(contactId = contactId, navController = navController)
        }
    }

}