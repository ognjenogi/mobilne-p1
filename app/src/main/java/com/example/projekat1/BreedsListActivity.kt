package com.example.projekat1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.projekat1.breeds.BreedDetailsViewModel
import com.example.projekat1.breeds.BreedListViewModel
import com.example.projekat1.nav.AppNavigation
import com.example.projekat1.ui.theme.Projekat1Theme

class BreedsListActivity : ComponentActivity() {
    private val viewModel: BreedListViewModel by viewModels()
    private val viewModelDetails: BreedDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Projekat1Theme {
                AppNavigation(viewModel,viewModelDetails)
            }
        }
    }
}
