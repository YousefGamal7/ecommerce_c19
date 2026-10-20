package com.route.firstapp.ui.search

import androidx.lifecycle.viewModelScope
import com.route.domain.data.Resource
import com.route.domain.usecase.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase,
) : SearchContract.ViewModel() {

    private val _states = MutableStateFlow<SearchContract.SearchState>(SearchContract.SearchState.Idle)
    override val states: StateFlow<SearchContract.SearchState> = _states.asStateFlow()

    private val _events = Channel<SearchContract.SearchEvent>(Channel.BUFFERED)
    override val events: Flow<SearchContract.SearchEvent> = _events.receiveAsFlow()

    private var searchJob: Job? = null

    init {
        // Load initial products so the search screen immediately displays products on open
        searchProducts("")
    }

    override fun doIntent(intent: SearchContract.Intent) {
        when (intent) {
            is SearchContract.Intent.Search -> {
                searchProducts(intent.query)
            }
            SearchContract.Intent.LoadInitialProducts -> {
                searchProducts("")
            }
            SearchContract.Intent.ClearSearch -> {
                searchProducts("")
            }
        }
    }

    private fun searchProducts(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _states.emit(SearchContract.SearchState.Loading)
            searchProductsUseCase.searchProducts(keyword = query.ifBlank { null })
                .collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            _states.emit(SearchContract.SearchState.Loading)
                        }
                        is Resource.Success -> {
                            val products = resource.data ?: emptyList()
                            if (products.isEmpty() && query.isNotBlank()) {
                                _states.emit(SearchContract.SearchState.Empty(query = query))
                            } else {
                                _states.emit(SearchContract.SearchState.Success(products = products))
                            }
                        }
                        is Resource.Error -> {
                            _states.emit(
                                SearchContract.SearchState.Error(
                                    resource.throwable.localizedMessage ?: "Something Went Wrong"
                                )
                            )
                        }
                    }
                }
        }
    }
}
