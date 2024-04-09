package com.example.projekat1.breeds

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.rememberImagePainter
fun NavGraphBuilder.details(
    route: String,
    viewModel: BreedDetailsViewModel,
    navController: NavController
) = composable(
    route = route
) {navBackStackEntry->
    val breedId = navBackStackEntry.arguments?.getString("id")?: throw IllegalArgumentException("breed id is required.")
    LaunchedEffect(Unit) {
        viewModel.processIntent(BreedIntent.LoadBreedDetails(breedId))
    }
    val state = viewModel.state.collectAsState()
    BreedDetailsScreen(
        state = state.value,
        context = LocalContext.current
    )
}
@Composable
fun BreedDetailsScreen(
    state: BreedDetailsState,
    context: Context,
) {

    when (state) {
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                if (state.pic != null) {
                    val imgR=state.pic.get(0)
                    item {
                        Image(
                            painter = rememberImagePainter(data = imgR.url),
                            contentDescription = state.breed.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(shape = RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )  }

                }

                item {  Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Text(
                        text = state.breed.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                item {  Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Text(
                        text = state.breed.description,
                        fontSize = 16.sp
                    )
                }
                item {  Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Text(
                        text = "Origin countries: ${state.breed.origin}",
                        fontSize = 16.sp
                    )
                }
                item {  Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Text(
                        text = "Temperament:",
                        fontSize = 16.sp
                    )
                    val temperamentList = state.breed.temperament.split(",")
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
                item {  Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Text(
                        text = "Lifespan: ${state.breed.life_span}",
                        fontSize = 16.sp
                    )
                }
                item {  Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Text(
                        text = "adaptability: ${state.breed.adaptability}",
                        fontSize = 16.sp
                    )
                }
                item {  Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Text(
                        text = "grooming: ${state.breed.grooming}",
                        fontSize = 16.sp
                    )
                }
                item {  Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Text(
                        text = "hairless: ${state.breed.hairless}",
                        fontSize = 16.sp
                    )
                }
                item {  Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Text(
                        text = "experimental: ${state.breed.experimental}",
                        fontSize = 16.sp
                    )
                }
                item {  Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Text(
                        text = "natural: ${state.breed.natural}",
                        fontSize = 16.sp
                    )
                }
                item {  Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Button(
                        onClick = { openWikipediaPage(context, state.breed.wikipedia_url) },
                    ) {
                        Text(text = "Open Wikipedia")
                    }
                }
            }
        }
        is BreedDetailsState.Error -> {
            Text(text = "Error: ${state.message}")
        }
    }

}

private fun openWikipediaPage(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}
