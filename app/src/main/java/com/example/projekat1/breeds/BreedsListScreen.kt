package com.example.projekat1.breeds

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.breeds(
    route:String,
    viewModel: BreedListViewModel,
    onBreedClick: (Breed) -> Unit,
    navController: NavController)= composable(
    route = route
){
    val breedsState by viewModel.state.collectAsState()
    BreedsListScreen(state = breedsState, onBreedClick = onBreedClick, intentPublisher = {
        viewModel.processIntent(it)
    })
}
@Composable
fun BreedsListScreen(
    state: BreedListState,
    onBreedClick:  (Breed) -> Unit,
    intentPublisher:  (BreedIntent) -> Unit
) {
    when (state ) {
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


            Column(modifier = Modifier.padding(16.dp)) {
                var searchText by remember { mutableStateOf("") }
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        intentPublisher(BreedIntent.SearchByName(searchText))
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Search Breeds")
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    items(state.breeds) { breed ->
                        BreedListItem(breed = breed, onItemClick = { onBreedClick(breed)})
                        Divider()
                    }
                }
            }
        }
        is BreedListState.Error -> {
            Text(text = "Error: ${state.message}")
        }
    }

}

@Composable
fun BreedListItem(
    breed: Breed,
    onItemClick:  () -> Unit
) {
    val temperamentList = breed.temperament.split(",")
    val description = if (breed.description.length > 250) {
        breed.description.substring(0, 250) + "..."
    } else {
        breed.description
    }
    Surface(
        modifier = Modifier.run {
            fillMaxWidth()
                .clickable(onClick = onItemClick)
                .padding(vertical = 8.dp, horizontal = 16.dp)
        },
        color = Color.LightGray,
    ) {
        Column {
            Text(text = breed.name, fontWeight = FontWeight.Bold)
            Text(text = "Alternative names: ${breed.alt_names}")
            Text(text = "Description: ${description}")
            val x = if (temperamentList.size % 2 == 0) {
                temperamentList.size / 2
            } else {
                temperamentList.size / 2 + 1
            }
            Row {
                Column {
                    temperamentList.take(x).forEach { trait ->
                        Chip(text = trait.trim())
                    }
                }
                Column {
                    temperamentList.takeLast(temperamentList.size -x).forEach { trait ->
                        Chip(text = trait.trim())
                    }
                }
            }
    }
}
}

@Composable
fun Chip(text: String) {
    Surface(
        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp),
        color = Color.Gray
    ) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}