package com.example.projekat1.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.projekat1.breeds.BreedDetailsScreen
import com.example.projekat1.breeds.BreedDetailsState
import com.example.projekat1.breeds.BreedDetailsViewModel
import com.example.projekat1.breeds.BreedIntent
import com.example.projekat1.breeds.BreedListState
import com.example.projekat1.breeds.BreedListViewModel
import com.example.projekat1.breeds.BreedsListScreen

object Controller {

    @Composable
    fun onClickDet(breedId:String,detailsViewModel: BreedDetailsViewModel){

        val breedsState by detailsViewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            detailsViewModel.processIntent(BreedIntent.LoadBreedDetails(breedId))
        }

        when (val state = breedsState) {
            is BreedDetailsState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        progress = 0.89f,
                    )
                }

            }
            is BreedDetailsState.Success -> {
                val context = LocalContext.current
                BreedDetailsScreen(state,context)
            }
            is BreedDetailsState.Error -> {
                Text(text = "Error: ${state.message}")
            }
        }
    }

    @SuppressLint("UnrememberedMutableState", "SuspiciousIndentation")
    @Composable
    fun onClickSearch(name: String, navController: NavHostController, viewModel: BreedListViewModel) {

        val breedsState by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            if (name.isEmpty())
                viewModel.processIntent(BreedIntent.LoadBreeds)
            else
                viewModel.processIntent(BreedIntent.SearchByName(name=name))
        }

        when (val state = breedsState) {
            is BreedListState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        progress = 0.89f,
                    )
                }

            }
            is BreedListState.Success -> {

                BreedsListScreen(
                    state = state,
                    onBreedClick = { breed ->
                        navController.navigate(route = "det/breeds?id=${breed.id}")
                    },
                    onSearchClick = { searchText ->
                        navController.navigate("breeds/search?name=$searchText")
                    }
                )
            }
            is BreedListState.Error -> {
                Text(text = "Error: ${state.message}")
            }
        }
    }
    @Composable
    fun showAll(navController: NavHostController, viewModel: BreedListViewModel) {
        Log.d("Uslo","isdass")

        val breedsState by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.processIntent(BreedIntent.LoadBreeds)
        }

        when (val state = breedsState) {
            is BreedListState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        progress = 0.89f,
                    )
                }

            }
            is BreedListState.Success -> {

                BreedsListScreen(
                    state= state,
                    onBreedClick = { breed ->
                        navController.navigate(route = "det/breeds?id=${breed.id}")
                    },
                    onSearchClick = { searchText ->
                        navController.navigate("breeds/search?name=$searchText")
                    }
                )
            }
            is BreedListState.Error -> {
                Text(text = "Error: ${state.message}")
            }
        }
    }
}