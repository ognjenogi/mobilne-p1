package com.example.projekat1.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

import com.example.projekat1.breeds.BreedDetailsViewModel
import com.example.projekat1.breeds.BreedListViewModel
import com.example.projekat1.breeds.breeds
import com.example.projekat1.breeds.details
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun AppNavigation(viewModel:BreedListViewModel,detailsViewModel: BreedDetailsViewModel) {

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    ModalBottomSheetLayout(bottomSheetNavigator) {
        NavHost(
            navController = navController,
            startDestination = "breeds",
        ) {
            breeds(route = "breeds",viewModel, onBreedClick ={ navController.navigate(route = "det/breeds?id=${it.id}")},navController)
            details( route = "det/breeds?id={id}",detailsViewModel,navController)
        }
    }
}
