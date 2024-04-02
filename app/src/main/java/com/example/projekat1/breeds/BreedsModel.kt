package com.example.projekat1.breeds
data class Breed(
    val id: String,
    val name: String,
    val alt_names: String,
    val description: String,
    var temperament: String,
)
data class Weight(
    val imperial: String,
    val metric: String
)

data class BreedDetails(
    val id: String,
    val name: String,
    val alt_names: String,
    val description: String,
    var temperament: String,
    val origin: String,
    val life_span: String,
    val weight: Weight,
    val adaptability:Int,
    val grooming:Int,
    val hairless:Int,
    val experimental:Int,
    val natural:Int,
    val wikipedia_url:String
)
data class CatImage(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)
