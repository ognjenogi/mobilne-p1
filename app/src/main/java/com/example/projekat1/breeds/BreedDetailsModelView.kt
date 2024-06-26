package com.example.projekat1.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekat1.repository.BreedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class BreedDetailsState {
    object Loading : BreedDetailsState()
    data class Success(val breed: BreedDetails, val pic: List<CatImage>?) : BreedDetailsState()
    data class Error(val message: String) : BreedDetailsState()
}

class BreedDetailsViewModel : ViewModel() {
    private val _state = MutableStateFlow<BreedDetailsState>(BreedDetailsState.Loading)
    val state: StateFlow<BreedDetailsState> = _state
    private val intents = MutableStateFlow<BreedIntent>(BreedIntent.LoadBreedDetails(id =""))
    init {
        observeIntents()
    }

    fun processIntent(intent: BreedIntent) {
        viewModelScope.launch{
            intents.emit(intent)
        }
    }
    private fun observeIntents(){
        viewModelScope.launch{
            intents.collect{
                if(it is BreedIntent.LoadBreedDetails) loadBreedDetails(it.id)
            }
        }
    }
    private fun loadBreedDetails(id:String) {
        viewModelScope.launch {
            _state.value = BreedDetailsState.Loading
            try {
                withContext(Dispatchers.IO) {
                val breed = BreedRepository.findById(id)
                val img = BreedRepository.findPictureById(id)
                _state.value = BreedDetailsState.Success(breed!!, img)
            }
            } catch (e: Exception) {
                _state.value = BreedDetailsState.Error("Error loading breeds: ${e.message}")
            }
        }
    }


}

