package com.example.projekat1.breeds

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

@Composable
fun BreedDetailsScreen(
    state: BreedDetailsState.Success,
    context: Context,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (state.pic != null) {
            val imgR=state.pic.get(0)
            Image(
                painter = rememberImagePainter(data = imgR.url),
                contentDescription = state.breed.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = state.breed.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = state.breed.description,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Origin countries: ${state.breed.origin}",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Temperament: ${state.breed.temperament}",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Lifespan: ${state.breed.life_span}",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "adaptability: ${state.breed.adaptability}",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "grooming: ${state.breed.grooming}",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "hairless: ${state.breed.hairless}",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "experimental: ${state.breed.experimental}",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "natural: ${state.breed.natural}",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { openWikipediaPage(context, state.breed.wikipedia_url) },
        ) {
            Text(text = "Open Wikipedia")
        }
    }
}

private fun openWikipediaPage(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}
