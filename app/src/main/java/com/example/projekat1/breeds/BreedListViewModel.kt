package com.example.projekat1.breeds
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekat1.repository.BreedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class BreedListIntent {
    object LoadBreeds : BreedListIntent()
    data class SearchByName(val name: String) : BreedListIntent()
}

sealed class BreedListState {
    object Loading : BreedListState()
    data class Success(val breeds: List<Breed>) : BreedListState()
    data class Error(val message: String) : BreedListState()
}

class BreedListViewModel : ViewModel() {

    private val _state = MutableStateFlow<BreedListState>(BreedListState.Loading)
    val state: StateFlow<BreedListState> = _state

    init {
        loadBreeds()
    }

    fun processIntent(intent: BreedListIntent) {
        when (intent) {
            is BreedListIntent.LoadBreeds -> loadBreeds()
            is BreedListIntent.SearchByName -> searchBreedsByName(intent.name)
        }
    }

    private fun loadBreeds() {
        viewModelScope.launch {
            _state.value = BreedListState.Loading
            try {
                val breeds = BreedRepository.getBreeds()
                _state.value = BreedListState.Success(breeds!!)
            } catch (e: Exception) {
                _state.value = BreedListState.Error("Error loading breeds: ${e.message}")
            }
        }
    }

    private fun searchBreedsByName(name: String) {
        viewModelScope.launch {
            _state.value = BreedListState.Loading
            try {
                val breeds = BreedRepository.searchBreedsByName(name)
                _state.value = BreedListState.Success(breeds!!)
            } catch (e: Exception) {
                _state.value = BreedListState.Error("Error searching breeds: ${e.message}")
            }
        }
    }
}

