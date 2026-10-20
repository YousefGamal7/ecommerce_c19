package com.route.firstapp.ui.search

import androidx.lifecycle.ViewModel as AndroidViewModel
import com.route.domain.data.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

abstract class SearchContract {

    abstract class ViewModel : AndroidViewModel() {
        abstract val states: StateFlow<SearchState>
        abstract val events: Flow<SearchEvent>
        abstract fun doIntent(intent: Intent)
    }

    sealed class SearchState {
        data object Idle : SearchState()
        data object Loading : SearchState()
        data class Success(val products: List<Product>) : SearchState()
        data class Empty(val query: String) : SearchState()
        data class Error(val message: String) : SearchState()
    }

    sealed class Intent {
        data class Search(val query: String) : Intent()
        data object LoadInitialProducts : Intent()
        data object ClearSearch : Intent()
    }

    sealed class SearchEvent {
        data class ShowToast(val message: String) : SearchEvent()
    }
}
