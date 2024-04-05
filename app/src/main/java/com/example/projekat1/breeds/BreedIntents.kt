package com.example.projekat1.breeds

sealed class BreedIntent {
    object LoadBreeds : BreedIntent()
    data class SearchByName(val name: String) : BreedIntent()
    class LoadBreedDetails(val id: String) : BreedIntent()
}
