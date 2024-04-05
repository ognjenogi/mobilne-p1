package com.example.projekat1.nav

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.projekat1.breeds.BreedDetailsScreen
import com.example.projekat1.breeds.BreedDetailsState
import com.example.projekat1.breeds.BreedDetailsViewModel
import com.example.projekat1.breeds.BreedIntent
import com.example.projekat1.breeds.BreedListState
import com.example.projekat1.breeds.BreedListViewModel
import com.example.projekat1.breeds.BreedsListScreen
import com.example.projekat1.repository.Controller.onClickDet
import com.example.projekat1.repository.Controller.onClickSearch
import com.example.projekat1.repository.Controller.showAll
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
            composable(
                route = "breeds",
            ) {
                Log.d("Uslo","isdass")

                showAll(navController = navController,viewModel=viewModel)
            }

            composable(
                route = "det/breeds?id={id}",
                arguments = listOf(
                    navArgument(name = "id") {
                        this.nullable = true
                        this.type = NavType.StringType
                    }
                ),
            ) {
                navBackStackEntry ->
                val breedId = navBackStackEntry.arguments?.getString("id")
                Log.d("Ubime", "ID = $breedId")
                if (breedId != null) {
                    onClickDet(breedId = breedId,detailsViewModel)
                }
            }
            composable(
                route = "breeds/search?name={name}",
                arguments = listOf(
                    navArgument("name") {
                        nullable = true
                        type = NavType.StringType
                    }
                )
            ) {
                    navBackStackEntry ->
                val name = navBackStackEntry.arguments?.getString("name")
                if (name != null) {
                    onClickSearch(name,navController,viewModel)
                }
            }


        }
    }
}
